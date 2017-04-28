package ecoo.dao;

import ecoo.data.upload.Upload;
import ecoo.data.upload.UploadStatus;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UploadDao extends BaseDao<Integer, Upload> {

    /**
     * Returns the {@link Integer} representation of the number of occurances that the given mapping
     * appears.
     *
     * @param uploadId  The pk of the upload to exclude.
     * @param mappingId The pk of the mapping to evaluate.
     * @return The count.
     */
    int countByMapping(Integer uploadId, Integer mappingId);

    /**
     * Returns the {@link Integer} representation of the number of uploads that match the status
     * criteria.
     *
     * @param status The status to evaluate.
     * @return The count.
     * @throws IllegalArgumentException If status is null or empty.
     */
    int countByStatus(UploadStatus.Status... status);

    /**
     * Method used to parse the given upload.
     *
     * @param upload the upload to parse
     */
    void parse(Upload upload);

    /**
     * Methos used to execute the upload process and actually upload the data from the upload file
     * into the relevant tables.
     *
     * @param upload           the upload to run the upload for
     * @param pathToUploadFile the absolute path to the upload file
     * @param pathToXmlFile    the absolute path to the xml mapping file
     */
    void runFileUpload(Upload upload, String pathToUploadFile, String pathToXmlFile);

    /**
     * Method used to bulk update the status of the given uploads.
     *
     * @param status  the new status
     * @param uploads the uploads to update
     */
    void updateStatus(UploadStatus.Status status, Upload... uploads);

    /**
     * Returns a collection of uploads that match the given upload status/statuses.
     *
     * @param status the status to evaluate
     * @return a collection of uploads
     */
    List<Upload> findUploadsByStatus(UploadStatus.Status... status);

}
