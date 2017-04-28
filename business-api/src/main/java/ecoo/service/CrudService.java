package ecoo.service;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CrudService<P, M> {

    /**
     * Returns the total number of entities.
     *
     * @return The count.
     */
    long count();


    /**
     * Returns all the entities.
     *
     * @return A collection.
     */
    Collection<M> findAll();

    /**
     * Returns the entity for the given id.
     *
     * @param id The unique id.
     * @return The entity or null
     */
    M findById(P id);

    /**
     * Method to insert or update the given entity.
     *
     * @param entity The entity to save.
     * @return The saved entity.
     */
    M save(M entity);

    /**
     * Method to delete the given entity.
     *
     * @param entity The entity to deleted.
     * @return The deleted entity.
     */
    M delete(M entity);
}
