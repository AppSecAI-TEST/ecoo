package ecoo.ws.upload.rest;

import ecoo.data.upload.*;
import ecoo.service.FeatureService;
import ecoo.service.UploadService;
import ecoo.ws.common.command.DownloadRowDataCommand;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.upload.convert.ImportToImportListRowConverter;
import ecoo.ws.upload.convert.NewUploadRequestToImportConverter;
import ecoo.ws.upload.json.NewUploadRequest;
import ecoo.ws.upload.json.ProcessUploadsResponse;
import ecoo.ws.upload.json.QueryUploadDataResponse;
import ecoo.ws.upload.json.UploadListRow;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/uploads")
public class UploadResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(UploadResource.class);

    private UploadService importService;

    private FeatureService featureService;

    private ImportToImportListRowConverter importToImportListRowConverter;

    private NewUploadRequestToImportConverter newUploadRequestToImportConverter;

    @Autowired
    public UploadResource(UploadService importService, FeatureService featureService, ImportToImportListRowConverter importToImportListRowConverter, NewUploadRequestToImportConverter newUploadRequestToImportConverter) {
        this.importService = importService;
        this.featureService = featureService;
        this.importToImportListRowConverter = importToImportListRowConverter;
        this.newUploadRequestToImportConverter = newUploadRequestToImportConverter;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/query/id/{uploadId}/criteria/{criteria}/start/{start}/pageSize/{pageSize}/totalRecords/{totalRecords}", method = RequestMethod.GET)
    public ResponseEntity<QueryUploadDataResponse> query(@PathVariable Integer uploadId
            , @PathVariable String criteria
            , @PathVariable Integer start
            , @PathVariable Integer pageSize
            , @PathVariable Integer totalRecords) throws IOException {
        Assert.notNull(uploadId, "System cannot complete. No upload id defined.");

        final Upload aUpload = importService.findByPrimaryId(uploadId);
        Assert.notNull(aUpload, String.format("System cannot complete. No upload found for id %s.", uploadId));

        final Collection<UploadData> data = importService.findUploadDataByStatus(aUpload, null, start, start + pageSize);
        return ResponseEntity.ok(new QueryUploadDataResponse(totalRecords, totalRecords, data));
    }

    @RequestMapping(value = "/id/{id}/data/count", method = RequestMethod.GET)
    public ResponseEntity<Long> count(@PathVariable Integer id) throws IOException {
        Assert.notNull(id);
        final Upload aUpload = importService.findByPrimaryId(id);
        return ResponseEntity.ok(importService.countUploadDataByStatus(aUpload));
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ResponseEntity<ProcessUploadsResponse> process(@RequestBody Collection<Integer> uploadIds) throws IOException {
        final Collection<Upload> queuedUploads = new ArrayList<>();
        final Collection<Upload> scheduledUploads = new ArrayList<>();

        for (final Integer uploadId : uploadIds) {
            final Upload aUpload = importService.findByPrimaryId(uploadId);
            queuedUploads.add(aUpload);
            importService.submitToUploadQueue(aUpload, currentUser());
        }
        return ResponseEntity.ok(new ProcessUploadsResponse(queuedUploads, scheduledUploads));
    }

//    @RequestMapping(value = "/id/{id}/data/claims", method = RequestMethod.GET)
//    public ResponseEntity<Collection<ClaimUploadData>> findClaimUploadData(@PathVariable Integer id) throws IOException {
//        Assert.notNull(id);
//        return ResponseEntity.ok(importService.findClaimUploadData(id));
//    }


    @SuppressWarnings("unused")
    @RequestMapping(value = "/id/{id}/data/export", method = RequestMethod.GET)
    public void exportFailedRowsAndDownloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws IOException {
        Assert.notNull(id);

        final Upload aUpload = importService.findByPrimaryId(id);

        final List<List<String>> rowData = importService.export(aUpload);

        final DownloadRowDataCommand downloadRowDataCommand = new DownloadRowDataCommand();
        downloadRowDataCommand.execute(response, rowData);
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Upload> find(@PathVariable Integer id) {
        return ResponseEntity.ok(importService.findByPrimaryId(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Upload> addUpload(@RequestBody NewUploadRequest request) throws IOException {
        Assert.notNull(request);

        final Upload aUpload = newUploadRequestToImportConverter.convert(request);
        importService.save(aUpload.getMapping(), currentUser());
        importService.addUpload(aUpload, currentUser());

        return ResponseEntity.ok(aUpload);
    }


    @SuppressWarnings("ThrowFromFinallyBlock")
    @RequestMapping(method = RequestMethod.POST, value = "/csvFile")
    public ResponseEntity<PreviewCsvFile> buildCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        Assert.notNull(file);
        Assert.isTrue(!file.isEmpty());

        BufferedOutputStream stream = null;
        try {
            final File srcFile = createAndStoreTemporaryFile(file);

            stream = new BufferedOutputStream(new FileOutputStream(srcFile));
            FileCopyUtils.copy(file.getInputStream(), stream);

            final CsvFile csvFile = new CsvFile(srcFile);

            final Map<Integer, String> firstRow = new HashMap<>();

            final Map<Integer, List<PreviewCsvFileRow>> rows = new HashMap<>();
            for (final Integer rowIndex : csvFile.getRowsMap().keySet()) {
                if (rowIndex >= 10) break;
                rows.put(rowIndex, new ArrayList<>());

                final CsvFile.CsvRow csvRow = csvFile.getRowsMap().get(rowIndex);
                for (Integer columnIndex : csvRow.getColumns().keySet()) {
                    if (rowIndex == 0) {
                        firstRow.put(columnIndex, csvRow.getData(columnIndex));
                    }
                    rows.get(rowIndex).add(new PreviewCsvFileRow(columnIndex, csvRow.getData(columnIndex)));
                }
            }

            final PreviewCsvFile previewCsvFile = new PreviewCsvFile(
                    csvFile.getName()
                    , csvFile.getAbsolutePath()
                    , csvFile.getColumnsMap().keySet()
                    , firstRow
                    , rows
            );

            return ResponseEntity.ok(previewCsvFile);

        } catch (final IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;

        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @RequestMapping(value = "/requiredFields/uploadType/{uploadTypeId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<RequiredField>> findRequiredFieldsByType(@PathVariable String uploadTypeId) {
        Assert.hasLength(uploadTypeId);

        final UploadType.Type uploadType = UploadType.Type.getTypeByPrimaryId(uploadTypeId);
//        switch (uploadType) {
//            case Claim:
//                return ResponseEntity.ok(new ClaimUpload().getRequiredFields());
//            case Product:
//                return ResponseEntity.ok(new ProductUpload().getRequiredFields());
//            default:
        throw new DataIntegrityViolationException(String.format("No upload class "
                + "defined for upload type \"%s\".", uploadType.name()));
//        }
    }

    @RequestMapping(value = "/requiredFieldMappings/uploadType/{uploadType}", method = RequestMethod.GET)
    public ResponseEntity<Collection<RequiredFieldMapping>> findRequiredFieldMappingByType(@PathVariable String uploadType) {
        final UploadType.Type aUploadType = UploadType.Type.getTypeByPrimaryId(uploadType);

        final Collection<RequiredFieldMapping> requiredFields
                = new ArrayList<>(importService.findRequiredFieldMappingByType(aUploadType));
        return ResponseEntity.ok(requiredFields);
    }

    @RequestMapping(value = "/requiredFieldMappings/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<RequiredFieldMapping> findRequiredFieldMappingById(@PathVariable Integer id) {
        return ResponseEntity.ok(importService.findRequiredFieldMappingByPrimaryId(id));
    }

    @RequestMapping(value = "/requiredFieldMappings", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequiredFieldMapping> saveRequiredFieldMapping(@RequestBody RequiredFieldMapping requiredFieldMapping) {
        Assert.notNull(requiredFieldMapping);
        Assert.hasText(requiredFieldMapping.getName());
        Assert.notNull(requiredFieldMapping.getUploadTypeId());
        Assert.notNull(requiredFieldMapping.getMappingItems());
        Assert.isTrue(!requiredFieldMapping.getMappingItems().isEmpty());

        importService.save(requiredFieldMapping, currentUser());
        return ResponseEntity.ok(requiredFieldMapping);
    }

    @RequestMapping(value = "/requiredFieldMappings/remove", method = RequestMethod.POST)
    public ResponseEntity<RequiredFieldMapping> deleteRequiredFieldMapping(@RequestBody RequiredFieldMapping requiredFieldMapping) {
        Assert.notNull(requiredFieldMapping);
        Assert.isTrue(!requiredFieldMapping.isNew());

        importService.delete(requiredFieldMapping, currentUser());
        return ResponseEntity.ok(requiredFieldMapping);
    }

    @RequestMapping(value = "/remove/id/{id}", method = RequestMethod.POST)
    public ResponseEntity<Upload> deleteUpload(@PathVariable Integer id) {
        Assert.notNull(id);

        final Upload aUpload = importService.findByPrimaryId(id);
        importService.delete(aUpload, currentUser());
        return ResponseEntity.ok(aUpload);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<UploadListRow>> findAll() {
        final Collection<UploadListRow> rowData = importService.findAll().stream()
                .map(aUpload -> importToImportListRowConverter.convert(aUpload))
                .collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(rowData);
    }


    private File createAndStoreTemporaryFile(@RequestParam("file") MultipartFile file) {
        final String tempDir = featureService.tempDirectory()
                + UUID.randomUUID().toString().replace("-", "");

        final File targetDir = new File(tempDir);
        if (!targetDir.exists() && targetDir.mkdirs()) {
            LOG.info(String.format("Directory %s created.", targetDir.getAbsolutePath()));
        }

        final File srcFile = new File(targetDir.getAbsolutePath(), file.getOriginalFilename());
        LOG.info(srcFile.getAbsolutePath());
        return srcFile;
    }

}
