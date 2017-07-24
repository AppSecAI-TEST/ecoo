package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentActivityGroupDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
@Transactional
public class ShipmentActivityGroupDaoImplTest {

    @Autowired
    private ShipmentActivityGroupDao shipmentActivityGroupDao;

    @Test
    public void testDeleteAllActivitiesByShipment() throws Exception {
        shipmentActivityGroupDao.deleteAllActivitiesByShipment(1);
    }
}