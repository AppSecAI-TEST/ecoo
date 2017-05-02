package ecoo.dao.impl.hibernate;

import ecoo.dao.EndpointDao;
import ecoo.data.Endpoint;
import ecoo.data.EndpointStat;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "endpointDao")
public class EndpointDaoImplImpl extends BaseHibernateDaoImpl<Integer, Endpoint> implements EndpointDao {

    @Autowired
    public EndpointDaoImplImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Endpoint.class);
    }

    /**
     * Returns all the endpoint stats for the given API.
     *
     * @param endpointId        The API id.
     * @param effectiveFromDate The from which to start.
     * @return A collection.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<EndpointStat> findByApiAndAfterRequestedDate(Integer endpointId, Date effectiveFromDate) {
        Assert.notNull(endpointId);
        Assert.notNull(effectiveFromDate);

        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EndpointStat.class, "stat");
        detachedCriteria.add(Restrictions.eq("stat.apiId", endpointId));
        detachedCriteria.add(Restrictions.ge("stat.requestedTime", effectiveFromDate));

        return (Collection<EndpointStat>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    /**
     * Returns all the health stats for the given API.
     *
     * @param endpointId The endpoint to evaluate.
     * @return A collection.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<EndpointStat> findStatsByEndpoint(Integer endpointId) {
        Assert.notNull(endpointId);

        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(EndpointStat.class, "stat");
        detachedCriteria.add(Restrictions.eq("stat.apiId", endpointId));

        return (Collection<EndpointStat>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    /**
     * Method to save endpoint stat.
     *
     * @param endpointStat The stat to save.
     * @return The saved stat.
     */
    @Override
    public EndpointStat save(EndpointStat endpointStat) {
        Assert.notNull(endpointStat);

        getHibernateTemplate().save(endpointStat);
        return endpointStat;
    }
}
