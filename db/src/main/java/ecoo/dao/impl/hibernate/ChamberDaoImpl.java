package ecoo.dao.impl.hibernate;

import ecoo.dao.ChamberDao;
import ecoo.data.Chamber;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Repository(value = "chamberDao")
public class ChamberDaoImpl extends BaseAuditLogDaoImpl<Integer, Chamber> implements ChamberDao {

    @Autowired
    public ChamberDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Chamber.class);
    }
}
