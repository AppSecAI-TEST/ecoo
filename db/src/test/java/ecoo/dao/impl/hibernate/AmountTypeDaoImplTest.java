package ecoo.dao.impl.hibernate;

import ecoo.dao.AmountTypeDao;
import ecoo.data.AmountType;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class AmountTypeDaoImplTest {

    @Autowired
    private AmountTypeDao amountTypeDao;


    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindAmountTypesBySchema() {
        List<AmountType> data = amountTypeDao.findAmountTypesBySchema("");
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() == 0);

        data = amountTypeDao.findAmountTypesBySchema("INC");
        Assert.assertNotNull(data);
        Assert.assertTrue(data.size() > 0);
    }
}