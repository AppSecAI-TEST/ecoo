package ecoo.dao.impl.hibernate;

import ecoo.dao.BaseDao;
import ecoo.data.BaseModel;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The following class is a super class for all Data Access Objects. It provides three public
 * methods :find,update,save and three protected hook methods which allow anyone to change the
 * implementation of children as they see fit. This class must be extended by all Dao classes.
 *
 * @author Muhammed Patel
 * @since April 2017
 */
abstract class BaseHibernateDaoImpl<P extends Serializable, M extends BaseModel<P>> extends
        HibernateDaoSupport implements BaseDao<P, M> {

    private Class<M> baseModelClass;

    /**
     * Constructor a new Base Hibernate Data Access Object.
     *
     * @param baseModelClass the class type you'd like to persist
     */
    protected BaseHibernateDaoImpl(SessionFactory sessionFactory, final Class<M> baseModelClass) {
        if (sessionFactory == null) {
            throw new IllegalArgumentException("sessionFactory cannot be null.");
        }
        setSessionFactory(sessionFactory);

        if (baseModelClass == null) {
            throw new IllegalArgumentException("baseModelClass cannot be null.");
        }
        this.baseModelClass = baseModelClass;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.dao.BaseDao#countAll()
     */
    @SuppressWarnings("unchecked")
    @Override
    public long countAll() {
        String sql = "select count(*)" +
                " from " + baseModelClass.getCanonicalName() + " model";
        Collection<Long> data = (Collection<Long>) getHibernateTemplate().find(sql);
        if (data == null || data.isEmpty()) {
            return 0L;
        }
        return data.iterator().next();
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.dao.BaseDao#createNewEntity()
     */
    @Override
    public M createNewEntity() {
        return null;
    }

    /**
     * Returns the Class object for the qualified base model.
     *
     * @return the class object
     */
    protected final Class<M> getBaseModelClass() {
        return baseModelClass;
    }

    /**
     * Returns a model based on the unique identifier.
     *
     * @param primaryId The model's unique identifier.
     * @return The found model - null if not found.
     */
    @Override
    public M findByPrimaryId(P primaryId) {
        if (primaryId == null) {
            return null;
        }
        return getHibernateTemplate().get(baseModelClass, primaryId);
    }

    /**
     * This class will be used by Services and Managers.
     *
     * @return list of instances of class found in database
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<M> findAll() {
        return (Collection<M>) getHibernateTemplate().find("from " + baseModelClass.getCanonicalName());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean mergeThenSave(M model) {
        if (model == null) {
            return true;

        } else if (model.isNew()) {
            return save(model);

        } else {
            getHibernateTemplate().merge(model);
            return afterSave(model);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public final boolean save(M model) {
        if (model == null) {
            return true;

        } else if (model.isNew()) {
            return beforeSave(model) && insert(model);

        } else {
            if (beforeSave(model)) {
                getHibernateTemplate().saveOrUpdate(model);
                return afterSave(model);
            } else {
                return false;
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean insert(M model) {
        if (model == null) {
            return true;
        }
        if (beforeSave(model)) {
            getHibernateTemplate().save(model);
            afterSave(model);
        }
        return true;
    }

    /**
     * Hook save method used to provide additional functionality to sub classes.
     *
     * @param model The model to persist.
     * @return true if save successful.
     */
    protected boolean beforeSave(M model) {
        return true;
    }

    protected boolean afterSave(M model) {
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean delete(M model) {
        if (model == null) {
            return false;
        }

        if (model.isNew()) {
            return true;
        }

        model = getHibernateTemplate().merge(model);
        getHibernateTemplate().delete(model);
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean deleteAll(Collection<M> models) {
        if (models == null || models.isEmpty()) {
            return false;
        }
        models.forEach(this::delete);
        return true;
    }


    /**
     * Method used to delete a model from the database.
     *
     * @param id The primary key of the object to delete.
     * @return true if successfully deleted.
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean deleteById(P id) {
        if (id == null) return false;
        final M model = findByPrimaryId(id);
        if (model != null) {
            getHibernateTemplate().delete(model);
        }
        return true;
    }

    /**
     * Returns all the product ids.
     *
     * @return The product ids.
     */
    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    @Override
    public List<P> findAllIds() {
        final String sql = "select primaryId" +
                " from " + getBaseModelClass().getCanonicalName() + " model";
        return (List<P>) getHibernateTemplate().find(sql);
    }
}
