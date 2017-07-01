package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberRateDao;
import ecoo.data.ChamberRate;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class ChamberRateDaoImplTest {

    @Autowired
    private ChamberRateDao chamberRateDao;


    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindRateByChamberAndMemberIndicator() {
        ChamberRate data = chamberRateDao.findRateByChamberAndMemberIndicator(-99, false);
        Assert.assertNull(data);

        data = chamberRateDao.findRateByChamberAndMemberIndicator(1, false);
        Assert.assertNotNull(data);

        data = chamberRateDao.findRateByChamberAndMemberIndicator(1, true);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindRatesByChamber() {
        final Collection<ChamberRate> data = chamberRateDao.findRatesByChamber(1);
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() > 0);
    }

    @Test
    public void testFindAll() {
        final Collection<ChamberRate> data = chamberRateDao.findAll();
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() > 0);
    }
}