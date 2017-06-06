package ecoo.service.impl;

import ecoo.dao.FeatureDao;
import ecoo.data.Feature;
import ecoo.service.FeatureService;
import ecoo.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service(value = "featureService")
public class FeatureServiceImpl extends AuditTemplate<Integer, Feature, FeatureDao> implements FeatureService {

    private FeatureDao featureDao;

    @Autowired
    public FeatureServiceImpl(FeatureDao featureDao) {
        super(featureDao);
        this.featureDao = featureDao;
    }

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    @Override
    public String tempDirectory() {
        return FileUtils.resolveDir(homeDirectory()) + FileUtils.resolveDir("temp");
    }

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    @Override
    public String exportDirectory() {
        return FileUtils.resolveDir(homeDirectory())
                + FileUtils.resolveDir("export");
    }

    /**
     * Returns the String representation of the APP_HOME/system directory.
     *
     * @return The application home directory.
     */
    @Override
    public String systemDirectory() {
        return FileUtils.resolveDir(homeDirectory())
                + FileUtils.resolveDir("system");
    }

    /**
     * Returns the String representation of the APP_HOME directory.
     *
     * @return The application home directory.
     */
    @Override
    public String homeDirectory() {
        final Feature applicationHome = findByName(Feature.Type.APP_HOME);
        Assert.notNull(applicationHome, "APP_HOME not found.");
        Assert.hasText(applicationHome.getValue(), "APP_HOME not defined.");
        return applicationHome.getValue();
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
        return featureDao.findByName(name);
    }

    /**
     * Returns the feature for the given name.
     *
     * @param type The name to evaluate.
     * @return The feature or null.
     */
    @Override
    public Feature findByName(Feature.Type type) {
        Assert.notNull(type);
        return featureDao.findByName(type.name());
    }
}
