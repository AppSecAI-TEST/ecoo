package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.data.ShipmentActivity;
import ecoo.data.ShipmentActivityGroup;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Repository(value = "shipmentActivityGroupDao")
public class ShipmentActivityGroupDaoImpl extends BaseAuditLogDaoImpl<Integer, ShipmentActivityGroup>
        implements ShipmentActivityGroupDao {

    @Autowired
    public ShipmentActivityGroupDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ShipmentActivityGroup.class);
    }

    /**
     * Method to delete all activities by shipment.
     *
     * @param shipmentId The pk of the shipment.
     */
    @Override
    public void deleteAllActivitiesByShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");

        Query deleteActivitiesQuery = getSessionFactory().getCurrentSession().createQuery("delete from "
                + ShipmentActivity.class.getSimpleName() + " s where s.groupId in(select g.primaryId from "
                + ShipmentActivityGroup.class.getSimpleName() + " g where g.shipmentId = " + shipmentId + ")");
        deleteActivitiesQuery.executeUpdate();

        Query deleteGroupQuery = getSessionFactory().getCurrentSession().createQuery("delete from "
                + ShipmentActivityGroup.class.getSimpleName() + " g where g.shipmentId = " + shipmentId);
        deleteGroupQuery.executeUpdate();
    }

    @Override
    protected boolean afterSave(ShipmentActivityGroup model) {
        model.getActivities().stream().filter(line -> line.getGroupId() == null).forEach(line -> {
            line.setGroupId(model.getPrimaryId());
            getHibernateTemplate().merge(line);
        });
        return true;
    }
}
