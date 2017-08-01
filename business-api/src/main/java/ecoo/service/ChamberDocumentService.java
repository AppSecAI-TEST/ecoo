package ecoo.service;

import ecoo.data.ChamberDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ChamberDocumentService extends CrudService<Integer, ChamberDocument>, AuditedModelAware<Integer, ChamberDocument> {

    /**
     * Returns the chamber document for the chamber and the type.
     *
     * @param chamberId       The chamber id.
     * @param documentType The document type.
     * @return The chamber document or null.
     */
    ChamberDocument findByChamberAndType(Integer chamberId, String documentType);

    /**
     * Returns a list of chamber documents.
     *
     * @param chamberId The chamber pk.
     * @return The list of documents.
     */
    List<ChamberDocument> findByChamber(Integer chamberId);
}
