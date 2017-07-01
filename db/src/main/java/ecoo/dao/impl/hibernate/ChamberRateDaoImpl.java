package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberRateDao;
import ecoo.data.ChamberRate;
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
@Repository(value = "chamberRateDao")
public class ChamberRateDaoImpl extends BaseAuditLogDaoImpl<Integer, ChamberRate> implements ChamberRateDao {

    @Autowired
    public ChamberRateDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ChamberRate.class);
    }

    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @param member    The member indicator.
     * @return The list of associated Chambers.
     */
    @SuppressWarnings("unchecked")
    @Override
    public ChamberRate findRateByChamberAndMemberIndicator(Integer chamberId, boolean member) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        final List<ChamberRate> data = (List<ChamberRate>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_RATES_BY_CHAMBER_AND_MEMBER"
                , new String[]{"chamberId", "member"}
                , new Object[]{chamberId, member});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }

    /**
     * Returns a list of Chamber rates for the given chamber.
     *
     * @param chamberId The chamber id to evaluate.
     * @return The list of associated Chambers.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ChamberRate> findRatesByChamber(Integer chamberId) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        return (List<ChamberRate>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_RATES_BY_CHAMBER", "chamberId", chamberId);
    }
}
