package ecoo.service;

import ecoo.data.SystemJob;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface SystemJobService extends CrudService<Integer, SystemJob> {

    /**
     * The method to save a new system job.
     *
     * @param systemJob The system job to save.
     * @return The saved system job.
     */
    SystemJob register(SystemJob systemJob);

    /**
     * Returns all the system jobs for the given class name.
     *
     * @param className The class name to evaluate.
     * @return The collection.
     */
    Collection<SystemJob> findByClassName(String className);

    /**
     * Returns all the most recent jobs for each class name.
     *
     * @return The collection.
     */
    Collection<SystemJob> findMostRecentJobs();
}
