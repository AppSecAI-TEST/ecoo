package ecoo.dao.impl.hibernate;

import ecoo.dao.UserDao;
import ecoo.data.Role;
import ecoo.data.User;
import ecoo.data.UserStatus;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "userDao")
public class UserDaoImpl extends BaseAuditLogDaoImpl<Integer, User> implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory
            , @Qualifier("spivDataSource") DataSource dataSource) {
        super(sessionFactory, User.class);
        Assert.notNull(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Returns a collection users associated to the given role.
     *
     * @param role The role to evaluate.
     * @return A collection of users.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> findByRole(Role role) {
        Assert.notNull(role, "The variable role cannot be null.");

        return (List<User>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_USERS_BY_ROLE"
                , "role", role.name());
    }

    /**
     * Returns all users in the given status.
     *
     * @param status The status to evaluate.
     * @return A collection of users.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<User> findByStatus(UserStatus status) {
        Assert.notNull(status);

        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "user");
        detachedCriteria.add(Restrictions.eq("user.status", status.id()));

        return (Collection<User>) getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    /**
     * Returns the count of all users in the given status.
     *
     * @param status The status to evaluate.
     * @return The count.
     */
    @SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
    @Override
    public long countByStatus(UserStatus status) {
        Assert.notNull(status);
        try {
            final SqlRowSet resultSet = jdbcTemplate.queryForRowSet("select count(id) count "
                    + "from user_acc where status = ?", status.id());
            if (resultSet == null) return 0L;
            if (resultSet.next()) {
                return resultSet.getLong("count");
            }
            return 0L;

        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }

    /**
     * Returns the user for the given username.
     *
     * @param username The username to evaluate.
     * @return The user or null.
     */
    @Override
    public User findByUsername(String username) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "user");
        detachedCriteria.add(Restrictions.eq("user.username", username));

        final List<?> data = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (User) data.iterator().next();
    }

    /**
     * Returns the user for the given personal reference.
     *
     * @param type  The personal reference type.
     * @param value The personal reference value.
     * @return The user or null.
     */
    @Override
    public User findByPersonalReference(String type, String value) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "user");
        detachedCriteria.add(Restrictions.eq("user.personalReferenceType", type));
        detachedCriteria.add(Restrictions.eq("user.personalReferenceValue", value));

        final List<?> data = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (User) data.iterator().next();
    }

    /**
     * Returns the user for the given primary email address.
     *
     * @param primaryEmailAddress The primary email address.
     * @return The user or null.
     */
    @Override
    public User findByPrimaryEmailAddress(String primaryEmailAddress) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "user");
        detachedCriteria.add(Restrictions.eq("user.primaryEmailAddress", primaryEmailAddress));

        final List<?> data = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (User) data.iterator().next();
    }

    /**
     * Returns the user for the given mobile number.
     *
     * @param mobileNumber The mobile number.
     * @return The user or null.
     */
    @Override
    public User findByMobileNumber(String mobileNumber) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "user");
        detachedCriteria.add(Restrictions.eq("user.mobileNumber", mobileNumber));

        final List<?> data = getHibernateTemplate().findByCriteria(detachedCriteria);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (User) data.iterator().next();
    }
}
