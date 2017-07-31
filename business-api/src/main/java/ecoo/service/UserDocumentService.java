package ecoo.service;

import ecoo.data.UserDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface UserDocumentService extends CrudService<Integer, UserDocument>, AuditedModelAware<Integer, UserDocument> {

    /**
     * Returns the user document for the user and the type.
     *
     * @param userId       The user id.
     * @param documentType The document type.
     * @return The user document or null.
     */
    UserDocument findByUserAndType(Integer userId, String documentType);

    /**
     * Returns a list of user documents.
     *
     * @param userId The user pk.
     * @return The list of documents.
     */
    List<UserDocument> findByUser(Integer userId);
}
