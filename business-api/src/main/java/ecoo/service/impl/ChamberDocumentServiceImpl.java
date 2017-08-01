package ecoo.service.impl;

import ecoo.dao.ChamberDocumentDao;
import ecoo.data.ChamberDocument;
import ecoo.service.ChamberDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public class ChamberDocumentServiceImpl extends AuditTemplate<Integer, ChamberDocument, ChamberDocumentDao>
        implements ChamberDocumentService {

    private ChamberDocumentDao chamberDocumentDao;

    @Autowired
    public ChamberDocumentServiceImpl(ChamberDocumentDao chamberDocumentDao) {
        super(chamberDocumentDao);
        this.chamberDocumentDao = chamberDocumentDao;
    }

    /**
     * Returns the chamber document for the chamber and the type.
     *
     * @param chamberId    The chamber id.
     * @param documentType The document type.
     * @return The chamber document or null.
     */
    @Override
    public ChamberDocument findByChamberAndType(Integer chamberId, String documentType) {
        return chamberDocumentDao.findByChamberAndType(chamberId, documentType);
    }

    /**
     * Returns a list of chamber documents.
     *
     * @param chamberId The chamber pk.
     * @return The list of documents.
     */
    @Override
    public List<ChamberDocument> findByChamber(Integer chamberId) {
        return chamberDocumentDao.findByChamber(chamberId);
    }
}
