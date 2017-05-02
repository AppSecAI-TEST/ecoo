package ecoo.dao.impl.hibernate;

import ecoo.dao.MetricTypeDao;
import ecoo.data.MetricType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "metricTypeDao")
public class MetricTypeDaoImpl extends BaseHibernateDaoImpl<String, MetricType> implements MetricTypeDao {

    /**
     * Constructs a new Data Access Object with the model bound to this Dao.
     */
    @Autowired
    public MetricTypeDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, MetricType.class);
    }
}
