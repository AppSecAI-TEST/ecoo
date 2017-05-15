package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberAdminDao;
import ecoo.data.ChamberAdmin;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class ChamberAdminDaoImplTest {

    @Autowired
    private ChamberAdminDao chamberAdminDao;


    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindByChamberAndUser() {
        final List<ChamberAdmin> data = chamberAdminDao.findByUser(1);
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() > 0);
    }

    @Test
    public void testFindAll() {
        final Collection<ChamberAdmin> data = chamberAdminDao.findAll();
        Assert.assertNotNull(data);
    }
}