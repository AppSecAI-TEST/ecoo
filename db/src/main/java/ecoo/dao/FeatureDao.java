package ecoo.dao;

import ecoo.data.Feature;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface FeatureDao extends AuditLogDao<Integer, Feature> {

    /**
     * Returns the feature for the given name.
     *
     * @param name The name to evaluate.
     * @return The feature or null.
     */
    Feature findByName(String name);

}
