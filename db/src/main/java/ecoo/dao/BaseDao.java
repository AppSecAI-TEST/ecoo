package ecoo.dao;

import java.util.Collection;
import java.util.List;

public interface BaseDao<P, M> {

    /**
     * Returns the {@link Long} representation of the number of rows in the table.
     *
     * @return the number of rows in the table.
     */
    long countAll();

    /**
     * Returns a model based on the unique identifier.
     *
     * @param primaryId The model's unique identifier.
     * @return The found model - null if not found.
     */
    M findByPrimaryId(P primaryId);

    /**
     * Creates a new object.
     *
     * @return The new unsaved model.
     */
    M createNewEntity();

    /**
     * This class will be used by Services and Managers.
     *
     * @return list of instances of class found in database
     */
    Collection<M> findAll();

    /**
     * Method used to persist (insert/update) an object.
     *
     * @param model The model to persist.
     * @return true if save successful.
     */
    boolean save(M model);

    boolean mergeThenSave(M model);

    boolean insert(M model);

    /**
     * Method used to delete a model from the database.
     *
     * @param model The object to delete.
     * @return true if successfully deleted.
     */
    boolean delete(M model);

    /**
     * Method used to delete a collection of models from the database.
     *
     * @param models The models to delete.
     * @return models if deleted successfully
     */
    boolean deleteAll(Collection<M> models);

    /**
     * Method used to delete a model from the database.
     *
     * @param id The primary key of the object to delete.
     * @return true if successfully deleted.
     */
    boolean deleteById(P id);

    /**
     * Returns all the product ids.
     *
     * @return The product ids.
     */
    List<P> findAllIds();
}