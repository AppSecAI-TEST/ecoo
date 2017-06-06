package ecoo.service.impl;

import au.com.bytecode.opencsv.CSVWriter;
import ecoo.dao.*;
import ecoo.data.User;
import ecoo.data.upload.*;
import ecoo.service.FeatureService;
import ecoo.service.UploadService;
import ecoo.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component(value = "uploadService")
public class UploadServiceImpl extends JdbcTemplateService<Integer, Upload> implements UploadService {

    private static final Logger LOG = LoggerFactory.getLogger(UploadServiceImpl.class);

    private UploadDao uploadDao;

    private RequiredFieldMappingDao requiredFieldMappingDao;

    private RequiredFieldMappingItemDao requiredFieldMappingItemDao;

    private UploadDataDao uploadDataDao;

    private UploadStatusDao uploadStatusDao;

    private UploadTypeDao uploadTypeDao;

    private FeatureService featureService;

    @Autowired
    public UploadServiceImpl(UploadDao uploadDao
            , RequiredFieldMappingDao requiredFieldMappingDao
            , RequiredFieldMappingItemDao requiredFieldMappingItemDao
            , UploadDataDao uploadDataDao
            , UploadStatusDao uploadStatusDao
            , UploadTypeDao uploadTypeDao
            , FeatureService featureService) {
        super(uploadDao);
        this.uploadDao = uploadDao;
        this.requiredFieldMappingDao = requiredFieldMappingDao;
        this.requiredFieldMappingItemDao = requiredFieldMappingItemDao;
        this.uploadDataDao = uploadDataDao;
        this.uploadStatusDao = uploadStatusDao;
        this.uploadTypeDao = uploadTypeDao;
        this.featureService = featureService;
    }

    /**
     * Method to update upload data in <b>NEW</b> transaction.
     *
     * @param anUpload The upload.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(Upload anUpload) {
        uploadDao.save(anUpload);
    }

    /**
     * Method to update upload data in <b>NEW</b> transaction.
     *
     * @param uploadData The upload entry.
     * @param status     The status.
     * @param message    The message.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(UploadData uploadData, UploadStatus.Status status, String message) {
        uploadData.setStatus(status.getPrimaryId());
        uploadData.setComments(message);
        uploadDataDao.save(uploadData);
    }

    /**
     * Method to submit an upload to process. This method will create a JMS message and
     * pop the message on the queue.
     *
     * @param anUpload       The upload to submit to the queue.
     * @param requestingUser The user actioning the request.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void submitToUploadQueue(final Upload anUpload, final User requestingUser) {
        try {
            anUpload.setStartTime(new Date());
            updateStatus(anUpload, UploadStatus.Status.Running);

            // FIXME:
//            switch (anUpload.getUploadType()) {
//                case Claim:
//                    submitUploadToQueue(anUpload, requestingUser, claimUploadJmsTemplate, claimUploadQueue);
//                    break;
//                case Product:
//                    processProductUpload(anUpload, requestingUser);
//                    break;
//                default:
//                    throw new IllegalArgumentException(String.format("Upload type %s not supported.", anUpload.getUploadType()));
//            }

        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            updateStatus(anUpload, UploadStatus.Status.ImportFailed);
        }
    }


    @SuppressWarnings("Duplicates")
    private void markUploadAsCompleted(final Upload anUpload) {
        final long uploadSuccessfulCount = countUploadDataByStatus(anUpload, UploadStatus.Status.UploadSuccessful);
        final long totalCount = countUploadDataByStatus(anUpload);
        if (uploadSuccessfulCount == totalCount) {
            anUpload.setStatus(UploadStatus.Status.UploadSuccessful.getPrimaryId());
        } else {
            final long uploadFailedCount = countUploadDataByStatus(anUpload, UploadStatus.Status.UploadFailed);
            if (uploadFailedCount > 0) {
                anUpload.setStatus(UploadStatus.Status.UploadFailed.getPrimaryId());
            } else {
                anUpload.setStatus(UploadStatus.Status.UploadPartial.getPrimaryId());
            }
        }
        anUpload.setEndTime(new Date());
        update(anUpload);
    }


    /**
     * Returns a collection of uploads that match the given upload status/statuses.
     *
     * @param status the status to evaluate
     * @return a collection of uploads
     */
    @Override
    public Collection<Upload> findUploadsByStatus(UploadStatus.Status... status) {
        return uploadDao.findUploadsByStatus(status);
    }

    /**
     * Returns the {@link Integer} representation of the number of occurances that the given mapping appears.
     *
     * @param importId  The pk of the import to exclude.
     * @param mappingId The pk of the mapping to evaluate.
     * @return The count.
     */
    @Override
    public int countByMapping(Integer importId, Integer mappingId) {
        return uploadDao.countByMapping(importId, mappingId);
    }

    /**
     * Returns the {@link Integer} representation of the number of imports that match the status criteria.
     *
     * @param status The status to evaluate.
     * @return The count.
     * @throws IllegalArgumentException If status is null or empty.
     */
    @Override
    public int countByStatus(UploadStatus.Status... status) {
        return uploadDao.countByStatus(status);
    }

    /**
     * Methods used to save an {@link UploadType} model.
     *
     * @param uploadType     The model to save.
     * @param requestingUser The currently logged in user.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(UploadType uploadType, User requestingUser) {
        Assert.notNull(uploadType);
        uploadTypeDao.save(uploadType);
    }

    /**
     * Returns the import data object for the given primary id.
     *
     * @param primaryId The primary id of the data record.
     * @param clazz     The sub data class.
     * @return The data object or null if none found.
     */
    @Override
    public <D extends UploadData> D findDataByPrimaryId(Integer primaryId, Class<D> clazz) {
        return uploadDataDao.findDataByPrimaryId(primaryId, clazz);
    }


    /**
     * Returns the bulk import for the given primary key.
     *
     * @param primaryId the primary key of the import
     * @return the import or null
     */
    @Override
    public Upload findByPrimaryId(Integer primaryId) {
        return uploadDao.findByPrimaryId(primaryId);
    }

    /**
     * Method used to start the given pending imports.
     *
     * @param uploads        the imports to start
     * @param requestingUser the current user
     * @throws IllegalArgumentException if an upload is not is status "Pending"
     * @throws IllegalArgumentException if an upload order number is undefined
     */
    @Override
    public void process(Collection<Upload> uploads, User requestingUser) {
        for (final Upload newUpload : uploads) {
            submitToUploadQueue(newUpload, requestingUser);
        }
    }

    /**
     * Method used to export the failed data for the given import.
     *
     * @param anUpload The chosen upload to export.
     * @return The exported rows.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<List<String>> export(Upload anUpload) throws IOException {
        Assert.notNull(anUpload);

        updateStatus(anUpload, UploadStatus.Status.Exporting);

        long start = System.currentTimeMillis();

        LOG.debug(String.format("Attempting to export upload #%s...", anUpload.getPrimaryId()));
        Collection<UploadData> dataToExport = gatherDataToExport(anUpload);

        if (dataToExport.isEmpty()) {
            LOG.debug("No data to export - no further processing required.");

            // Invoke parse to update the import's status.
            parse(anUpload);
            return new ArrayList<>();

        } else {
            LOG.debug(String.format("\tAbout to export a total of %s records.", dataToExport.size()));

            // Get the required fields for the column headings and ordering.
            final Collection<RequiredField> requiredFieldData = new TreeSet<>((o1, o2) ->
                    Integer.valueOf(o1.getExportOrder()).compareTo(o2.getExportOrder()));
            requiredFieldData.addAll(anUpload.getRequiredFields());

            final List<List<String>> rowData = new ArrayList<>();
            try {
                List<String> columnHeaders = requiredFieldData.stream()
                        .map(RequiredField::getName)
                        .collect(Collectors.toCollection(ArrayList::new));
                columnHeaders.add("comments");
                rowData.add(columnHeaders);


                for (UploadData data : dataToExport) {
                    final List<String> values = new ArrayList<>();
                    for (RequiredField requiredField : requiredFieldData) {
                        String value = requiredField.getValueAsString(data);
                        if (StringUtils.isNotBlank(value)) {
                            value = value.replace(String.valueOf(CSVWriter.DEFAULT_SEPARATOR), "");
                        }
                        values.add(value);
                    }
                    values.add(data.getComments());
                    rowData.add(values);
                }

                // Upload the status on the data associated to this upload.
                uploadDataDao.bulkUpdate(anUpload, dataToExport, UploadStatus.Status.Exported);
                LOG.debug(String.format("\tExported %s records successfully.", dataToExport.size()));

                // Invoke parse to update the import's status.
                parse(anUpload);

                // Finally copy the export file from the temp dir to the actual dir.
                return rowData;

            } catch (final Throwable t) {
                // bulkImport.setStatus(Status.ImportFailed.getPrimaryId());
                // importDao.save(bulkImport);
                LOG.error(t.getMessage(), t);

                return null;
            } finally {
                LOG.debug(String.format("Time taken to export data %sms.", (System.currentTimeMillis() - start)));
            }
        }
    }

    private Collection<UploadData> gatherDataToExport(Upload anUpload) {
        Collection<UploadData> alreadyExportedData = findUploadDataByStatus(anUpload, UploadStatus.Status.Exported);
        if (alreadyExportedData == null) alreadyExportedData = new ArrayList<>();

        LOG.debug(String.format("\tFound %s already exported records.", alreadyExportedData.size()));

        final Collection<UploadData> dataToExport = new HashSet<>();
        dataToExport.addAll(alreadyExportedData);

        Collection<UploadData> parsingFailedData = findUploadDataByStatus(anUpload, UploadStatus.Status.ParsingFailed);
        if (parsingFailedData == null) parsingFailedData = new ArrayList<>();
        dataToExport.addAll(parsingFailedData);
        LOG.debug(String.format("\tFound %s failed records.", parsingFailedData.size()));

        LOG.debug(String.format("\tUpload type is a %s upload - no special action required.",
                anUpload.getUploadType()));

        return dataToExport;
    }

    /**
     * Method used to parse the specific import.
     *
     * @param upload the import to parse
     */
    @Override
    public void parse(Upload upload) {
        Assert.notNull(upload);
        uploadDao.parse(upload);
    }

    /**
     * Method used to save the upload data changes.
     *
     * @param data the collection of data to save
     * @return true if saved successfully
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public <M extends UploadData> boolean save(Collection<M> data) {
        if (data == null) {
            return true;
        }
        for (UploadData d : data) {
            uploadDataDao.save(d);
        }
        return true;
    }

    /**
     * Method used to save the upload data changes.
     *
     * @param data the collection of data to save
     * @return true if saved successfully
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public <M extends UploadData> boolean save(M data) {
        uploadDataDao.save(data);
        return true;
    }

    /**
     * Method used to delete the given upload data.
     *
     * @param data The models to delete.
     * @return true if deleted successfully
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public <M extends UploadData> boolean delete(Collection<M> data) {
        if (data == null) {
            return true;
        }
        for (UploadData d : data) {
            uploadDataDao.delete(d);
        }
        return true;
    }

    @Override
    public Collection<UploadData> findUploadDataByStatus(Upload upload, UploadStatus.Status status, int start, int end) {
        return uploadDataDao.findUploadDataByStatus(upload, status, start, end);
    }

    @Override
    public Collection<UploadData> findUploadDataByStatus(Upload upload, UploadStatus.Status status) {
        return uploadDataDao.findUploadDataByStatus(upload, status);
    }

    @Override
    public long countUploadDataByStatus(Upload upload, UploadStatus.Status... status) {
        return uploadDataDao.countUploadDataByStatus(upload, status);
    }

    /**
     * The method used to add an import to the system which will automatically create the import file, import the import file to the repository and
     * then runs the parsing of the import.
     *
     * @param upload         the new upload to add
     * @param requestingUser the user adding the import
     * @throws IOException If io error occurs.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addUpload(Upload upload, User requestingUser) throws IOException {
        if (!upload.isNew()) {
            throw new DataIntegrityViolationException("cannot add an existing upload.");
        }

        updateStatus(upload, UploadStatus.Status.Importing);

        String fileName = upload.getFileName();

        // Create temporary file name
        final String pathToCsvTempFile = upload.getOriginalUploadFile().getAbsolutePath();

        final String pathToCsvCopyFile = featureService.exportDirectory()
                + StringUtils.trim(fileName);

        final String pathToXmlFormatFile = featureService.systemDirectory()
                + upload.getUploadType().getXmlFormatFileName();

        try {
            prepareImportFile0(upload, pathToCsvTempFile, pathToCsvCopyFile);
            uploadDao.runFileUpload(upload, pathToCsvCopyFile, pathToXmlFormatFile);

            parse(upload);

            LOG.info("Upload added... {}", upload.toString());

        } catch (final Throwable t) {
            LOG.error(t.getMessage(), t);
            updateStatus(upload, UploadStatus.Status.ImportFailed);
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateStatus(Upload upload, UploadStatus.Status status) {
        upload.setStatus(status.getPrimaryId());
        uploadDao.save(upload);
    }


    private void prepareImportFile0(Upload anUpload, String pathToCsvTempFile, String pathToCsvCopyFile)
            throws IOException {
        Assert.notNull(anUpload);
        Assert.notNull(anUpload.getCsvFileToUpload());
        Assert.notNull(anUpload.getMapping());

        // Update csv object to reflect first row.
        CsvFile csvFile = anUpload.getCsvFileToUpload();
        csvFile.setMapping(anUpload.getMapping());

        final Map<RequiredField, CsvFile.CsvColumn> columnExportMap = new TreeMap<>(
                (o1, o2) -> Integer.valueOf(o1.getExportOrder()).compareTo(o2.getExportOrder()));

        // Find all the CSVColumn that needs to be export and also the order of required export.
        for (final RequiredFieldMappingItem item : anUpload.getMapping().getMappingItems()) {
            RequiredField requiredField = anUpload.getRequiredFieldMap().get(item.getTableColumnName());
            columnExportMap.put(requiredField, csvFile.getCsvColumnBy(item.getCsvColumnIndex()));
        }

        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(new BufferedWriter((new FileWriter(new File(pathToCsvTempFile)))),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            boolean firstRow = true;
            for (final CsvFile.CsvRow csvRow : csvFile.getRowsMap().values()) {

                if (firstRow && csvFile.isFirstRowHeading()) {
                    firstRow = false;
                    continue;
                } else {
                    firstRow = false;
                }

                Collection<String> line = new LinkedList<>();

                // Set the default status for this csv row.
                UploadStatus.Status status = UploadStatus.Status.Ready;

                // The reason for putting the comma before the data, instead of after is two
                // fold. One , the file must end with a '\n'(new line) and not a ','(comma) and
                // second the first value is csv must be the id because of some quirk with bulk
                // uploads, but ours will be empty because of auto id generation on table. So
                // the out put of this is : ,Value1, Value2, etc...\n
                line.add("");

                // put the required field values into the array representing the line in the csvfile
                for (final Map.Entry<RequiredField, CsvFile.CsvColumn> exportEntry : columnExportMap.entrySet()) {
                    try {
                        String data = exportEntry.getValue().getData(csvRow);
                        RequiredField requiredField = exportEntry.getKey();

                        String val = StringUtils.stripToEmpty(requiredField.parse(data));
                        line.add(val.replace(String.valueOf(CSVWriter.DEFAULT_SEPARATOR), ""));

                    } catch (ParseException e) {
                        // If parsing failed, force the non parse value in.
                        line.add(exportEntry.getValue().getData(csvRow));
                        status = UploadStatus.Status.ParsingFailed;
                    }
                }

                // Add the status.
                line.add(status.getPrimaryId().toString());

                // Convert the list of values to String[] and write the line.
                csvWriter.writeNext(line.toArray(new String[line.size()]));
                csvWriter.flush();
            }

            // NOTE: Copy the csv file from the local temp dir to the proper repository. The reason
            // why we create a file a temp dir then copy it to the real dir is because more often
            // than not the real dir is on a network share and write a new file line by line is a
            // very painfully slow process... much easier creating the file locally then copy the
            // whole file once over the network.
            FileUtils.copy(pathToCsvTempFile, pathToCsvCopyFile, true);

        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }


    /**
     * Returns an import type model based on the given primary id.
     *
     * @param primaryId the unique identifier for the import type
     * @return the found model or null if none found
     */
    @Override
    public UploadType findUploadTypeByPrimaryId(String primaryId) {
        return uploadTypeDao.findByPrimaryId(primaryId);
    }

    /**
     * Returns an import status model based on the given primary id.
     *
     * @param primaryId the unique identifier for the import status
     * @return the found model or null if none found
     */
    @Override
    public UploadStatus findUploadStatusByPrimaryId(Integer primaryId) {
        return uploadStatusDao.findByPrimaryId(primaryId);
    }

    /**
     * Returns the default mapping for the give import type.
     *
     * @param type The upload type.
     * @return The default field mapping or null if none found.
     */
    @Override
    public RequiredFieldMapping findDefaultRequiredFieldMappingByType(UploadType.Type type) {
        return requiredFieldMappingDao.findDefaultRequiredFieldMappingByType(type);
    }

    /**
     * Returns a collection of all available upload mappings for the given import type.
     *
     * @param type The upload type.
     * @return The collection required fields.
     */
    @Override
    public Collection<RequiredFieldMapping> findRequiredFieldMappingByType(UploadType.Type type) {
        return requiredFieldMappingDao.findRequiredFieldMappingByType(type);
    }

    /**
     * Returns an required field mapping for the given primary key.
     *
     * @param id Th required field mapping id.
     * @return The collection required fields.
     */
    @Override
    public RequiredFieldMapping findRequiredFieldMappingByPrimaryId(Integer id) {
        return requiredFieldMappingDao.findByPrimaryId(id);
    }

    /**
     * Creates new upload mapping model for the given upload. The method will create and return a mapping model with default mapping details.
     *
     * @param anUpload       The bulk import associated to the mapping
     * @param setColumnIndex The column index.
     * @return the new mapping or null if the upload is null
     * @see Upload
     * @see RequiredFieldMapping
     * @see RequiredFieldMappingItem
     */
    @Override
    public RequiredFieldMapping createNewUploadMapping(Upload anUpload, boolean setColumnIndex) {
        Assert.notNull(anUpload);
        Assert.notNull(anUpload.getRequiredFields());
        Assert.notEmpty(anUpload.getRequiredFields());

        RequiredFieldMapping mapping = requiredFieldMappingDao.createNewEntity();

        // Set the correct upload mapping detail based on the upload type.
        List<RequiredFieldMappingItem> mappingItems = new LinkedList<>();

        // Populate the mapping with the required fields and with the default column index.
        List<RequiredField> requiredFields = new LinkedList<>(anUpload.getRequiredFields());
        for (int i = 0; i < requiredFields.size(); i++) {
            RequiredField requiredField = requiredFields.get(i);
            RequiredFieldMappingItem column = requiredFieldMappingItemDao.createNewEntity();
            column.setTableColumnName(requiredField.getName());
            mappingItems.add(column);

            if (setColumnIndex) {
                column.setCsvColumnIndex(i);
            }
        }
        mapping.setMappingItems(mappingItems);
        return mapping;
    }

    /**
     * Method used to persist the specific model.
     *
     * @param anUpload       the model to save
     * @param requestingUser the current logged in user
     * @return true if saved successfully
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(Upload anUpload, User requestingUser) {
        Assert.notNull(anUpload);
        Assert.notNull(requestingUser);
        return uploadDao.mergeThenSave(anUpload);
    }

    /**
     * Method used to persist the specific model.
     *
     * @param model          the model to save
     * @param requestingUser the current logged in user
     * @return true if saved successfully
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(RequiredFieldMapping model, User requestingUser) {
        return requiredFieldMappingDao.save(model);
    }

    /**
     * Method used to delete the specific model from persistance.
     *
     * @param anUpload       the model to delete
     * @param requestingUser the current logged in user
     * @return true if deleted successfully.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(Upload anUpload, User requestingUser) {
        Assert.notNull(anUpload);
        if (anUpload.isStatus(UploadStatus.Status.Running)) {
            throw new DataIntegrityViolationException(String.format("System cannot delete import %s. As the import is in" +
                    " status \"Running\" and therefore needs to complete before if can be deleted.", anUpload.getPrimaryId()));
        }
        return uploadDao.delete(anUpload);
    }

    /**
     * Method used to remove a mapping.
     *
     * @param mapping        The mapping to remove.
     * @param requestingUser The current logged in user.
     * @return <tt>true</tt>If deleted
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(RequiredFieldMapping mapping, User requestingUser) {
        Assert.notNull(mapping);
        Assert.notNull(requestingUser);

        if (uploadDao.countByMapping(null, mapping.getPrimaryId()) > 0) {
            throw new DataIntegrityViolationException("mapping linked.");
        }
        return requiredFieldMappingDao.delete(mapping);
    }

    /**
     * checks if the given uploadMapping name has been used to create another mapping
     *
     * @param type        the upload type
     * @param mappingName the name
     * @return true if there exists an uploadMapping that has the same upload mappingName
     */
    @Override
    public boolean isMappingNameDuplicate(UploadType.Type type, String mappingName) {
        return false;
    }
}
