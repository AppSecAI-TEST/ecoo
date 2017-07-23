package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.data.ShipmentActivityGroup;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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

    @Override
    protected boolean afterSave(ShipmentActivityGroup model) {
        model.getActivities().stream().filter(line -> line.getGroupId() == null).forEach(line -> {
            line.setGroupId(model.getPrimaryId());
            getHibernateTemplate().merge(line);
        });
        return true;
    }
}
