package ecoo.dao.impl.hibernate;

import ecoo.dao.FeatureDao;
import ecoo.data.Feature;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
@Transactional
public class FeatureDaoImplTest {

    @Autowired
    private FeatureDao featureDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAll() {
        final Collection<Feature> data = featureDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void testInsert() {
        final Feature feature = new Feature();
        feature.setName("NAME");
        feature.setValue("VALUE");
        feature.setDescription("Some description");
        featureDao.save(feature);

        final Feature otherFeature = featureDao.findByPrimaryId(feature.getPrimaryId());
        Assert.assertNotNull(otherFeature);
        Assert.assertSame("NAME", otherFeature.getName());
        Assert.assertSame("VALUE", otherFeature.getValue());
        Assert.assertSame("Some description", otherFeature.getDescription());
    }
}