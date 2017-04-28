package ecoo.service.impl;

import ecoo.data.Feature;
import ecoo.service.DocumentService;
import ecoo.service.FeatureService;
import ecoo.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    private FeatureService featureService;

    @Autowired
    public DocumentServiceImpl(FeatureService featureService) {
        this.featureService = featureService;
    }

    /**
     * Returns the {@link String} representation of the path for the given certificate.
     *
     * @param certificateId The certificate pk.
     * @return The path or directory.
     */
    @Override
    public String certificateOfOriginPath(Integer certificateId) {
        Assert.notNull(certificateId, "The variable certificateId cannot be null.");

        final Feature applicationHome = featureService.findByName(Feature.Type.APP_HOME);
        Assert.notNull(applicationHome, "APP_HOME not found.");
        Assert.hasText(applicationHome.getValue(), "APP_HOME not defined.");

        // C:\Users\Justin\.ecoo\docs\coo\1
        return FileUtils.resolveDir(applicationHome.getValue()) + "docs\\coo\\" + certificateId;
    }
}
