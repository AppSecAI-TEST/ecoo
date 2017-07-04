package ecoo.service.impl;

import ecoo.dao.BaseDao;
import ecoo.service.CrudService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class JdbcTemplateService<P, M> implements CrudService<P, M> {

    private BaseDao<P, M> dao;

    public JdbcTemplateService(BaseDao<P, M> dao) {
        this.dao = dao;
    }

    /**
     * Returns the total number of entities.
     *
     * @return The count.
     */
    @Override
    public final long count() {
        return dao.countAll();
    }

    /**
     * Returns all the entities.
     *
     * @return A collection.
     */
    @Override
    public final Collection<M> findAll() {
        return dao.findAll();
    }

    /**
     * Returns the entity for the given id.
     *
     * @param id The unique id.
     * @return The entity or null
     */
    @Override
    public final M findById(P id) {
        return dao.findByPrimaryId(id);
    }

    /**
     * Method to insert or update the given entity.
     *
     * @param entity The entity to save.
     * @return The saved entity.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public M save(M entity) {
        Assert.notNull(entity);
        beforeSave(entity);

        dao.mergeThenSave(entity);
        return entity;
    }

    /**
     * Method to insert or update the given entities.
     *
     * @param entities The entities to save.
     * @return The saved entities.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Collection<M> saveAll(Collection<M> entities) {
        Assert.notNull(entities);
        for (M entity : entities) {
            save(entity);
        }
        return entities;
    }

    /**
     * Method to delete all the given entities.
     *
     * @param entities The entities to delete.
     * @return The deleted entities.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Collection<M> deleteAll(Collection<M> entities) {
        Assert.notNull(entities);

        for (M entity : entities) {
            beforeDelete(entity);
        }

        dao.deleteAll(entities);
        return entities;
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    protected void beforeSave(M entity) {
    }

    /**
     * Method to delete the given entity.
     *
     * @param entity The entity to deleted.
     * @return The deleted entity.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public final M delete(M entity) {
        Assert.notNull(entity);
        beforeDelete(entity);

        dao.delete(entity);
        return entity;
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    protected void beforeDelete(M entity) {
    }
}
