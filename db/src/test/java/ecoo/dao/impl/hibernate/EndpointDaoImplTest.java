package ecoo.dao.impl.hibernate;

import ecoo.dao.EndpointDao;
import ecoo.data.Endpoint;
import ecoo.data.EndpointStat;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class EndpointDaoImplTest {

    @Autowired
    private EndpointDao endpointDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAll() {
        final Collection<Endpoint> data = endpointDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindStatsByEndpoint() {
        final Collection<EndpointStat> data = endpointDao.findStatsByEndpoint(1);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByApiAndAfterRequestedDate() {
        final Collection<EndpointStat> data = endpointDao.findByApiAndAfterRequestedDate(1, new Date());
        Assert.assertNotNull(data);
    }
}