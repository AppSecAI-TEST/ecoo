package ecoo.dao.impl.hibernate;

import ecoo.dao.MetricDao;
import ecoo.data.Metric;
import ecoo.data.MetricType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "metricDao")
public class MetricDaoImpl extends BaseAuditLogDaoImpl<Integer, Metric> implements MetricDao {

    /**
     * Constructs a new Data Access Object with the model bound to this Dao.
     */
    @Autowired
    public MetricDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Metric.class);
    }

    /**
     * Returns ALL the metrics for the the given user.
     *
     * @param userId The pk of the user to evaluate.
     * @return The metrics.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Metric> findByUser(Integer userId) {
        Assert.notNull(userId, "Variable userId cannot be null.");
        return (List<Metric>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_METRIC_BY_USER"
                , "userId", userId);
    }

    /**
     * Returns a list for the given metric type.
     *
     * @param metricType The metric type to evaluate.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Metric> findByType(MetricType.Type metricType) {
        Assert.notNull(metricType, "Variable metricType cannot be null.");
        return (List<Metric>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_METRIC_BY_TYPE"
                , "typeId", metricType.getPrimaryId());
    }

    /**
     * Returns the first element in the list for the given metric type.
     *
     * @param metricType The metric type to evaluate.
     * @return A metric or null.
     */
    @Override
    public Metric findFirstByType(MetricType.Type metricType) {
        final List<Metric> metrics = findByType(metricType);
        if (metrics == null || metrics.isEmpty()) return null;
        return metrics.iterator().next();
    }

    /**
     * Returns the metric for the the given user and type.
     *
     * @param userId     The pk of the user to evaluate.
     * @param metricType The metric type evaluate.
     * @return The metrics.
     */
    @Override
    public Metric findByUserAndType(Integer userId, MetricType.Type metricType) {
        Assert.notNull(userId, "Variable userId cannot be null.");
        Assert.notNull(metricType, "Variable metricType cannot be null.");

        final Collection<?> data = getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_METRIC_BY_USER_AND_TYPE"
                , new String[]{"userId", "typeId"}, new Object[]{userId, metricType.getPrimaryId()});
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (Metric) data.iterator().next();
    }
}
