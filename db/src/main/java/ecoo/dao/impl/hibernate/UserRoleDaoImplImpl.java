package ecoo.dao.impl.hibernate;

import ecoo.dao.UserRoleDao;
import ecoo.data.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "userRoleDao")
public class UserRoleDaoImplImpl extends BaseHibernateDaoImpl<Integer, UserRole> implements UserRoleDao {

    @Autowired
    public UserRoleDaoImplImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, UserRole.class);
    }
}
