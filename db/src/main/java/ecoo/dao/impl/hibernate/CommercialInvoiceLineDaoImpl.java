package ecoo.dao.impl.hibernate;

import ecoo.dao.CommercialInvoiceLineDao;
import ecoo.data.CommercialInvoiceLine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
}
