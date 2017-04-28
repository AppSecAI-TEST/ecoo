package ecoo.dao.impl.hibernate;

import ecoo.dao.FeatureDao;
import ecoo.data.Feature;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "featureDao")
public class FeatureDaoImpl extends BaseAuditLogDaoImpl<Integer, Feature> implements FeatureDao {

    @Autowired
    public FeatureDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Feature.class);
    }

    /**
     * Returns the feature for the given name.
     *
     * @param name The name to evaluate.
     * @return The feature or null.
     */
    @Override
    public Feature findByName(String name) {
        Assert.hasText(name);
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Feature.class, "feature");
        detachedCriteria.add(Restrictions.eq("feature.name", name));

        final List<?> data = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (Feature) data.iterator().next();
    }
}
