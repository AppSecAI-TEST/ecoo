package ecoo.dao.impl.hibernate;

import ecoo.dao.CommercialInvoiceDao;
import ecoo.data.CommercialInvoice;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "commercialInvoiceDao")
public class CommercialInvoiceDaoImpl extends BaseAuditLogDaoImpl<Integer, CommercialInvoice> implements CommercialInvoiceDao {

    @Autowired
    public CommercialInvoiceDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CommercialInvoice.class);
    }
}
