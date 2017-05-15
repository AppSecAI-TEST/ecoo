package ecoo.dao.impl.hibernate;

import ecoo.dao.UserSignatureDao;
import ecoo.data.UserSignature;
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
public class UserSignatureDaoImplTest {

    @Autowired
    private UserSignatureDao userSignatureDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAll() {
        final Collection<UserSignature> data = userSignatureDao.findAll();
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindByUser() {
        final Collection<UserSignature> data = userSignatureDao.findByUser(1);
        Assert.assertNotNull(data);
    }
}