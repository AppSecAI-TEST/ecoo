package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentDao;
import ecoo.data.Shipment;
import ecoo.data.audit.Revision;
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
import java.util.Map;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
@Transactional
public class ShipmentDaoImplTest {

    @Autowired
    private ShipmentDao shipmentDao;

    @BeforeClass
    public static void init() {
        BasicConfigurator.configure();
    }

    @Test
    public void testFindHistory() {
        final Map<Revision, Shipment> data = shipmentDao.findHistory(1);
        Assert.assertNotNull(data);
    }

    @Test
    public void testFindAll() {
        final Collection<Shipment> data = shipmentDao.findAll();
        Assert.assertNotNull(data);
    }
}