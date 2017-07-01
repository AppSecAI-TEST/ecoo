package ecoo.dao.impl.hibernate;

import ecoo.dao.CurrencyDao;
import ecoo.data.Currency;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Repository(value = "currencyDao")
public class CurrencyDaoImpl extends BaseAuditLogDaoImpl<String, Currency> implements CurrencyDao {

    @Autowired
    public CurrencyDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Currency.class);
    }
}
