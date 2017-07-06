package ecoo.dao.impl.hibernate;

import ecoo.dao.CommercialInvoiceLineDao;
import ecoo.data.CommercialInvoiceLine;
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
@Repository(value = "commercialInvoiceLineDao")
public class CommercialInvoiceLineDaoImpl extends BaseAuditLogDaoImpl<Integer, CommercialInvoiceLine>
        implements CommercialInvoiceLineDao {

    @Autowired
    public CommercialInvoiceLineDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CommercialInvoiceLine.class);
    }

    /**
     * Returns the list of lines for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CommercialInvoiceLine> findByShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return (List<CommercialInvoiceLine>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CI_LINES_BY_SHIPMENT", "shipmentId", shipmentId);
    }
}
