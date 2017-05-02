package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentDao;
import ecoo.data.Shipment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Repository(value = "shipmentDao")
public class ShipmentDaoImpl extends BaseAuditLogDaoImpl<Integer, Shipment> implements ShipmentDao {

    @Autowired
    public ShipmentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Shipment.class);
    }
}
