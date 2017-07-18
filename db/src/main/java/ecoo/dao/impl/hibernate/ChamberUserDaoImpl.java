package ecoo.dao.impl.hibernate;

import ecoo.convert.ObjectArrayToChamberUserListRowConverter;
import ecoo.dao.ChamberUserDao;
import ecoo.data.ChamberUser;
import ecoo.data.ChamberUserListRow;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
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

    /**
     * Returns users for a given chamber and member indicator.
     *
     * @param chamberId The chamber pk.
     * @return A list of users.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<ChamberUserListRow> findChamberUserListRowsByChamber(Integer chamberId) {
        Assert.notNull(chamberId, "The variable chamberId cannot be null.");
        final List<Object[]> data = (List<Object[]>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_USERS_BY_CHAMBER"
                , new String[]{"chamberId"}
                , new Object[]{chamberId});

        final Collection<ChamberUserListRow> rows = new ArrayList<>();
        for (Object[] objects : data) {
            rows.add(new ObjectArrayToChamberUserListRowConverter().convert(objects));
        }
        return rows;
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
