package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberUserDao;
import ecoo.data.ChamberUser;
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
@Repository(value = "chamberUserDao")
public class ChamberUserDaoImpl extends BaseAuditLogDaoImpl<Integer, ChamberUser> implements ChamberUserDao {

    @Autowired
    public ChamberUserDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ChamberUser.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ChamberUser findByChamberAndUser(Integer chamberId, Integer userId) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        Assert.notNull(userId, "The variable userId cannot be null.");
        final List<ChamberUser> data = (List<ChamberUser>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_USER_BY_CHAMBER_AND_USER"
                , new String[]{"chamberId", "userId"}
                , new Object[]{chamberId, userId});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChamberUser> findByUser(Integer userId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        return (List<ChamberUser>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_CHAMBER_USER_BY_USER", "userId", userId);
    }
}
