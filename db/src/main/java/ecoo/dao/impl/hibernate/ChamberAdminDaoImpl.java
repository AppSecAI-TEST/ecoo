package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberAdminDao;
import ecoo.data.ChamberAdmin;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Repository(value = "chamberAdminDao")
public class ChamberAdminDaoImpl extends BaseAuditLogDaoImpl<Integer, ChamberAdmin> implements ChamberAdminDao {

    @Autowired
    public ChamberAdminDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ChamberAdmin.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChamberAdmin> findByUser(Integer userId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        return (List<ChamberAdmin>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_ADMIN_BY_USER", "userId", userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ChamberAdmin findByUserAndChamber(Integer userId, Integer chamberId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");

        final List<ChamberAdmin> data = (List<ChamberAdmin>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_ADMIN_BY_USER_AND_CHAMBER"
                , new String[]{"userId", "chamberId"}
                , new Object[]{userId, chamberId});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }
}
