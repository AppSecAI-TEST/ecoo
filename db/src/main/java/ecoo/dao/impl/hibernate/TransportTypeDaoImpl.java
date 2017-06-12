package ecoo.dao.impl.hibernate;

import ecoo.dao.TransportTypeDao;
import ecoo.data.TransportType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "transportTypeDao")
public class TransportTypeDaoImpl extends BaseAuditLogDaoImpl<String, TransportType> implements TransportTypeDao {

    @Autowired
    public TransportTypeDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, TransportType.class);
    }
}
