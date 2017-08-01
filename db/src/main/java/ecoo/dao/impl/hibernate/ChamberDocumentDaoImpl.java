package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberDocumentDao;
import ecoo.data.ChamberDocument;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Repository(value = "chamberDocumentDao")
public class ChamberDocumentDaoImpl extends BaseAuditLogDaoImpl<Integer, ChamberDocument> implements ChamberDocumentDao {

    @Autowired
    public ChamberDocumentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ChamberDocument.class);
    }

    /**
     * Returns the chamber document for the chamber and the type.
     *
     * @param chamberId    The chamber id.
     * @param documentType The document type.
     * @return The chamber document or null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ChamberDocument findByChamberAndType(Integer chamberId, String documentType) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        Assert.hasText(documentType, "The variable documentType cannot be null.");
        final List<ChamberDocument> data = (List<ChamberDocument>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_DOCUMENT_BY_CHAMBER_AND_TYPE"
                        , new String[]{"chamberId", "documentType"}
                        , new Object[]{chamberId, documentType});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }

    /**
     * Returns the list of chamber documents for the given chamber.
     *
     * @param chamberId The chamber pk.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ChamberDocument> findByChamber(Integer chamberId) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        return (List<ChamberDocument>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_DOCUMENTS_BY_CHAMBER", "chamberId", chamberId);
    }
}
