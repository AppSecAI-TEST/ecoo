package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberUserDao;
import ecoo.data.ChamberUser;
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
public class ChamberUserDaoImplTest {

    @Autowired
    private ChamberUserDao chamberUserDao;


    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindByChamberAndUser() {
        final ChamberUser chamberAndUser = chamberUserDao.findByChamberAndUser(-1, -1);
        Assert.assertNull(chamberAndUser);
    }

    @Test
    public void testFindAll() {
        final Collection<ChamberUser> data = chamberUserDao.findAll();
        Assert.assertNotNull(data);
    }
}