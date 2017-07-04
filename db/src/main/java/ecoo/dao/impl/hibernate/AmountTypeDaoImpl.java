package ecoo.dao.impl.hibernate;

import ecoo.dao.AmountTypeDao;
import ecoo.data.AmountType;
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
@Repository(value = "amountTypeDao")
public class AmountTypeDaoImpl extends BaseAuditLogDaoImpl<String, AmountType> implements AmountTypeDao {

    private AmountTypeDao amountTypeDao;

    @Autowired
    public AmountTypeDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, AmountType.class);
        this.amountTypeDao = amountTypeDao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AmountType> findAmountTypesBySchema(String schema) {
        Assert.notNull(schema, "The variable schema cannot be null.");
        return (List<AmountType>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_AMOUNT_TYPES_BY_SCHEMA", "schema", schema);
    }
}
