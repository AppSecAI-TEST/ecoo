package ecoo.service;

import ecoo.data.Feature;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface FeatureService extends CrudService<Integer, Feature>, AuditedModelAware<Integer, Feature> {

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    String tempDirectory();

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    String exportDirectory();

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    String systemDirectory();

    /**
     * Returns the String representation of the APP_HOME directory.
     *
     * @return The application home directory.
     */
    String homeDirectory();

    /**
     * Returns the feature for the given name.
     *
     * @param name The name to evaluate.
     * @return The feature or null.
     */
    Feature findByName(String name);

    /**
     * Returns the feature for the given name.
     *
     * @param type The name to evaluate.
     * @return The feature or null.
     */
    Feature findByName(Feature.Type type);
}
