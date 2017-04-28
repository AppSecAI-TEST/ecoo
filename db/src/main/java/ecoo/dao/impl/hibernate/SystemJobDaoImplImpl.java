package ecoo.dao.impl.hibernate;

import ecoo.dao.SystemJobDao;
import ecoo.data.SystemJob;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "systemJobDao")
public class SystemJobDaoImplImpl extends BaseHibernateDaoImpl<Integer, SystemJob> implements SystemJobDao {

    private static final String FIND_MOST_RECENT_SYSTEM_JOB_BY_CLASSNAME = "FIND_MOST_RECENT_SYSTEM_JOB_BY_CLASSNAME";

    @Autowired
    public SystemJobDaoImplImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, SystemJob.class);
    }

    /**
     * Returns all the system jobs for the given class name.
     *
     * @param className The class name to evaluate.
     * @return The collection.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<SystemJob> findByClassName(String className) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SystemJob.class, "systemJob");
        detachedCriteria.add(Restrictions.eq("systemJob.className", className));
        return (Collection<SystemJob>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    /**
     * Returns all the most recent jobs for each class name.
     *
     * @return The collection.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<SystemJob> findMostRecentJobs() {
        return (Collection<SystemJob>) getHibernateTemplate().findByNamedQuery(FIND_MOST_RECENT_SYSTEM_JOB_BY_CLASSNAME);
    }
}
