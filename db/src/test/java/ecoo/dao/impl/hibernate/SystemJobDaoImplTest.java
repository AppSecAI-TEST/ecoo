package ecoo.dao.impl.hibernate;

import ecoo.dao.SystemJobDao;
import ecoo.data.SystemJob;
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
public class SystemJobDaoImplTest {

    @Autowired
    private SystemJobDao systemJobDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAll() {
        final Collection<SystemJob> data = systemJobDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void findByClassName() throws Exception {
        final Collection<SystemJob> data = systemJobDao.findByClassName("ClassName");
        Assert.assertNotNull(data);
    }

    @Test
    public void findMostRecentJobs() throws Exception {
        final Collection<SystemJob> data = systemJobDao.findMostRecentJobs();
        Assert.assertNotNull(data);
    }
}