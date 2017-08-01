package ecoo.dao;

import ecoo.data.ChamberDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ChamberDocumentDao extends AuditLogDao<Integer, ChamberDocument> {

    /**
     * Returns the user document for the user and the type.
     *
     * @param chamberId    The chamber id.
     * @param documentType The document type.
     * @return The user document or null.
     */
    ChamberDocument findByChamberAndType(Integer chamberId, String documentType);

    /**
     * Returns the list of user documents for the given user.
     *
     * @param chamberId The user pk.
     * @return A list.
     */
    List<ChamberDocument> findByChamber(Integer chamberId);
}
