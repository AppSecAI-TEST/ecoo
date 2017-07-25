package ecoo.service.impl;

import ecoo.dao.BaseDao;
import ecoo.data.BaseModel;
import ecoo.service.CrudService;
import org.elasticsearch.common.collect.Lists;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class ElasticsearchTemplateService<P extends Serializable
        , M extends BaseModel<P>> implements CrudService<P, M> {

    private BaseDao<P, M> dao;

    private ElasticsearchRepository<M, P> repository;

    private ElasticsearchTemplate elasticsearchTemplate;

    public ElasticsearchTemplateService(BaseDao<P, M> dao, ElasticsearchRepository<M, P> repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        this.dao = dao;
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * Returns a String where those characters that to be escaped are escaped by a preceding <code>\</code>.
     */
    String escape(String s) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '*' || c == '?' || c == '|' || c == '&' || c == '/') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Returns the total number of entities.
     *
     * @return The count.
     */
    @Override
    public final long count() {
        return repository.count();
    }

    /**
     * Returns all the entities.
     *
     * @return A collection.
     */
    @Override
    public final Collection<M> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    /**
     * Returns the entity for the given id.
     *
     * @param id The unique id.
     * @return The entity or null
     */
    @Override
    public final M findById(P id) {
        M entity = repository.findOne(id);
        if (entity == null) {
            entity = dao.findByPrimaryId(id);
            if (entity != null) {
                repository.save(entity);
            }
        }
        return entity;
    }

    protected final M reload(M entity) {
        return reload(entity.getPrimaryId());
    }

    protected final M reload(P id) {
        M entity = dao.findByPrimaryId(id);
        if (entity == null) {
            return null;
        } else {
            return repository.save(entity);
        }
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
        entity = reload(entity);

        afterSave(entity);
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
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */

    protected void beforeSave(M entity) {
    }

    /**
     * Method to delete all the given entities.
     *
     * @param entities The entities to delete.
     * @return The deleted entities.
     */
    @Override
    public Collection<M> deleteAll(Collection<M> entities) {
        for (M entity : entities) {
            beforeDelete(entity);

            dao.delete(entity);
            repository.delete(entity);
        }
        return entities;
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
        repository.delete(entity);

        afterDelete(entity);
        return entity;
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    protected void beforeDelete(M entity) {
    }

    /**
     * Method called before after is called.
     *
     * @param entity The entity to after.
     */

    protected void afterSave(M entity) {
    }

    /**
     * Method called before after is called.
     *
     * @param entity The entity to after.
     */
    protected void afterDelete(M entity) {
    }

    protected final List<String> queryForIds(SearchQuery searchQuery) {
        Assert.notNull(searchQuery, "The variable searchQuery cannot be null.");
        return elasticsearchTemplate.queryForIds(searchQuery);
    }
}
