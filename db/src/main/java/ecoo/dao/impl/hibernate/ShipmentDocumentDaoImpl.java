package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentDocumentDao;
import ecoo.data.ShipmentDocument;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "shipmentDocumentDao")
public class ShipmentDocumentDaoImpl extends BaseAuditLogDaoImpl<Integer, ShipmentDocument> implements ShipmentDocumentDao {

    @Autowired
    public ShipmentDocumentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ShipmentDocument.class);
    }

    /**
     * Returns the shipment document for the shipment and the type.
     *
     * @param shipmentId   The shipment pk.
     * @param documentType The document type.
     * @return The company document or null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ShipmentDocument findByShipmentAndType(Integer shipmentId, String documentType) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        Assert.hasText(documentType, "The variable documentType cannot be null.");
        final List<ShipmentDocument> data = (List<ShipmentDocument>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_DOCUMENTS_BY_SHIPMENT_AND_TYPE"
                        , new String[]{"shipmentId", "documentType"}
                        , new Object[]{shipmentId, documentType});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }

    /**
     * Returns the list of documents for the given shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ShipmentDocument> findByShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable companyId cannot be null.");
        return (List<ShipmentDocument>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_DOCUMENTS_BY_SHIPMENT"
                        , "shipmentId", shipmentId);
    }
}
