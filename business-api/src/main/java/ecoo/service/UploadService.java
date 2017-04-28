package ecoo.service;


import ecoo.data.upload.*;
import ecoo.data.User;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
public interface UploadService extends CrudService<Integer, Upload> {


    /**
     * Method to update upload data in <b>NEW</b> transaction.
     *
     * @param anUpload The upload.
     */
    void update(Upload anUpload);


    /**
     * Method to update upload data in <b>NEW</b> transaction.
     *
     * @param uploadData The upload entry.
     * @param status     The status.
     * @param message    The message.
     */
    void update(UploadData uploadData, UploadStatus.Status status, String message);

    /**
     * Method to submit an upload to process. This method will create a JMS message and
     * pop the message on the queue.
     *
     * @param newUpload      The upload to submit to the queue.
     * @param requestingUser The user actioning the request.
     */
    void submitToUploadQueue(Upload newUpload, User requestingUser);

    /**
     * Returns a collection of uploads that match the given upload status/statuses.
     *
     * @param status the status to evaluate
     * @return a collection of uploads
     */
    Collection<Upload> findUploadsByStatus(UploadStatus.Status... status);

    /**
     * Returns the {@link Integer} representation of the number of occurances that the given mapping appears.
     *
     * @param importId  The pk of the import to exclude.
     * @param mappingId The pk of the mapping to evaluate.
     * @return The count.
     */
    int countByMapping(Integer importId, Integer mappingId);

    /**
     * Returns the {@link Integer} representation of the number of imports that match the status criteria.
     *
     * @param status The status to evaluate.
     * @return The count.
     * @throws IllegalArgumentException If status is null or empty.
     */
    int countByStatus(UploadStatus.Status... status);

    /**
     * Methods used to save an {@link UploadType} model.
     *
     * @param uploadType     The model to save.
     * @param requestingUser The currently logged in user.
     */
    void save(UploadType uploadType, User requestingUser);

    /**
     * Returns the import data object for the given primary id.
     *
     * @param <D>       The sub data class.
     * @param primaryId The primary id of the data record.
     * @param clazz     The sub data class.
     * @return The data object or null if none found.
     */
    <D extends UploadData> D findDataByPrimaryId(Integer primaryId, Class<D> clazz);

    /**
     * Returns the bulk import for the given primary key.
     *
     * @param primaryId the primary key of the import
     * @return the import or null
     */
    Upload findByPrimaryId(Integer primaryId);

    /**
     * Method used to start the given pending imports.
     *
     * @param uploads        the imports to start
     * @param requestingUser the current user
     * @throws IllegalArgumentException if an upload is not is status "Pending"
     * @throws IllegalArgumentException if an upload order number is undefined
     */
    void process(Collection<Upload> uploads, User requestingUser);

    /**
     * Method used to export the failed data for the given import.
     *
     * @param upload the import to export
     * @return The exported rows.
     */
    List<List<String>> export(Upload upload) throws IOException;

    /**
     * Method used to parse the specific import.
     *
     * @param upload the import to parse
     */
    void parse(Upload upload);

    /**
     * Method used to save the upload data changes.
     *
     * @param data the collection of data to save
     * @return true if saved successfully
     */
    <M extends UploadData> boolean save(Collection<M> data);

    /**
     * Method used to save the upload data changes.
     *
     * @param data the collection of data to save
     * @return true if saved successfully
     */
    <M extends UploadData> boolean save(M data);

    /**
     * Method used to delete the given upload data.
     *
     * @param data The models to delete.
     * @return true if deleted successfully
     */
    <M extends UploadData> boolean delete(Collection<M> data);

    Collection<UploadData> findUploadDataByStatus(Upload upload, UploadStatus.Status status, int start, int end);

    Collection<UploadData> findUploadDataByStatus(Upload upload, UploadStatus.Status status);

    long countUploadDataByStatus(Upload upload, UploadStatus.Status... status);

    /**
     * The method used to add an import to the system which will automatically create the import file, import the import file to the repository and
     * then runs the parsing of the import.
     *
     * @param upload         the new upload to add
     * @param requestingUser the user adding the import
     * @throws IOException If io error occurs.
     */
    boolean addUpload(Upload upload, User requestingUser) throws IOException;

    /**
     * Returns an import type model based on the given primary id.
     *
     * @param primaryId the unique identifier for the import type
     * @return the found model or null if none found
     */
    UploadType findUploadTypeByPrimaryId(String primaryId);

    /**
     * Returns an import status model based on the given primary id.
     *
     * @param primaryId the unique identifier for the import status
     * @return the found model or null if none found
     */
    UploadStatus findUploadStatusByPrimaryId(Integer primaryId);

    /**
     * Returns a collection of all the available uploads.
     *
     * @return a collection of uploads
     */
    Collection<Upload> findAll();

    /**
     * Returns the default mapping for the give import type.
     *
     * @param type The import type.
     * @return The default field mapping or null if none found.
     */
    RequiredFieldMapping findDefaultRequiredFieldMappingByType(UploadType.Type type);

    /**
     * Returns a collection of all available upload mappings for the given import type.
     *
     * @return The collection required fields.
     */
    Collection<RequiredFieldMapping> findRequiredFieldMappingByType(UploadType.Type type);

    /**
     * Returns an required field mapping for the given primary key.
     *
     * @return The collection required fields.
     */
    RequiredFieldMapping findRequiredFieldMappingByPrimaryId(Integer id);

    /**
     * Creates new upload mapping model for the given upload. The method will create and return a mapping model with default mapping details.
     *
     * @param upload the bulk import associated to the mapping
     * @return the new mapping or null if the upload is null
     * @see Upload
     * @see RequiredFieldMapping
     * @see RequiredFieldMappingItem
     */
    RequiredFieldMapping createNewUploadMapping(Upload upload, boolean setColumnIndex);

    /**
     * Method used to persist the specific model.
     *
     * @param model          the model to save
     * @param requestingUser the current logged in user
     * @return true if saved successfully
     */
    boolean save(Upload model, User requestingUser);

    /**
     * Method used to persist the specific model.
     *
     * @param model          the model to save
     * @param requestingUser the current logged in user
     * @return true if saved successfully
     */
    boolean save(RequiredFieldMapping model, User requestingUser);

    /**
     * Method used to delete the specific model from persistance.
     *
     * @param model          the model to delete
     * @param requestingUser the current logged in user
     * @return true if deleted successfully.
     */
    boolean delete(Upload model, User requestingUser);

    /**
     * Method used to remove a mapping.
     *
     * @param mapping        The mapping to remove.
     * @param requestingUser The current logged in user.
     * @return <tt>true</tt>If deleted
     */
    boolean delete(RequiredFieldMapping mapping, User requestingUser);

    /**
     * checks if the given uploadMapping name has been used to create another mapping
     *
     * @param type        the upload type
     * @param mappingName the name
     * @return true if there exists an uploadMapping that has the same upload mappingName
     */
    boolean isMappingNameDuplicate(UploadType.Type type, String mappingName);
}
