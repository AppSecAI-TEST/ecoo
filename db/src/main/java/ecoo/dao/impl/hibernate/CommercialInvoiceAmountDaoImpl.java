package ecoo.dao.impl.hibernate;

import ecoo.dao.CommercialInvoiceAmountDao;
import ecoo.data.CommercialInvoiceAmount;
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
@Repository(value = "commercialInvoiceAmountDao")
public class CommercialInvoiceAmountDaoImpl extends BaseAuditLogDaoImpl<Integer, CommercialInvoiceAmount> implements CommercialInvoiceAmountDao {

    @Autowired
    public CommercialInvoiceAmountDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CommercialInvoiceAmount.class);
    }

    /**
     * Returns the list of amounts for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CommercialInvoiceAmount> findByShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return (List<CommercialInvoiceAmount>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CI_AMOUNTS_BY_SHIPMENT", "shipmentId", shipmentId);
    }
}
