package ecoo.dao;

import ecoo.data.upload.Upload;
import ecoo.data.upload.UploadData;
import ecoo.data.upload.UploadStatus;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UploadDataDao extends BaseDao<Integer, UploadData> {

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
     * Method used to bulk update the status for a collection of data records.
     *
     * @param anUpload  the owner of the data
     * @param data      the import data
     * @param newStatus the new status
     */
    void bulkUpdate(Upload anUpload, Collection<UploadData> data, UploadStatus.Status newStatus);

    /**
     * Method used to bulk update the upload data for a specific upload.
     *
     * @param anUpload  the owner of the data
     * @param oldStatus the previous status
     * @param newStatus the new status to change to
     */
    void bulkUpdateStatus(Upload anUpload, UploadStatus.Status oldStatus, UploadStatus.Status newStatus);

    /**
     * Returns a {@link Collection} of import data of the given search critera.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @param start    The start row index.
     * @param end      The end row index.
     * @return A collection of import data.
     */
    Collection<UploadData> findUploadDataByStatus(Upload anUpload, UploadStatus.Status status, int start, int end);

    /**
     * Returns a {@link Collection} of import data of the given search critera.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @return A collection of import data.
     */
    Collection<UploadData> findUploadDataByStatus(Upload anUpload, UploadStatus.Status... status);

    /**
     * Returns a {@link Long} representation of the number of rows that are in the given status.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @return The count.
     */
    long countUploadDataByStatus(Upload anUpload, UploadStatus.Status... status);
}
