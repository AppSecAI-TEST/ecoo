package ecoo.dao.impl.hibernate;

import ecoo.dao.CompanyDao;
import ecoo.data.Company;
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
public class CompanyDaoImplTest {

    @Autowired
    private CompanyDao companyDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindByRegistrationNo() {
        final Company data = companyDao.findByRegistrationNo("9021379012378231789231789");
        Assert.assertNull(data);
    }

    @Test
    public void testFindAll() {
        final Collection<Company> data = companyDao.findAll();
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() >= 0);
    }
}