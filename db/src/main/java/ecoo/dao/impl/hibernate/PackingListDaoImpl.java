package ecoo.dao.impl.hibernate;

import ecoo.dao.PackingListDao;
import ecoo.data.PackingList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "packingListDao")
public class PackingListDaoImpl extends BaseAuditLogDaoImpl<Integer, PackingList> implements PackingListDao {

    @Autowired
    public PackingListDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, PackingList.class);
    }
}
