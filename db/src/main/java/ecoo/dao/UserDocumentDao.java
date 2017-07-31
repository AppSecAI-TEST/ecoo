package ecoo.dao;

import ecoo.data.UserDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface UserDocumentDao extends AuditLogDao<Integer, UserDocument> {

    /**
     * Returns the user document for the user and the type.
     *
     * @param userId       The user id.
     * @param documentType The document type.
     * @return The user document or null.
     */
    UserDocument findByUserAndType(Integer userId, String documentType);

    /**
     * Returns the list of user documents for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    List<UserDocument> findByUser(Integer userId);
}
