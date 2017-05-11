package ecoo.dao.impl.hibernate;

import ecoo.dao.ShipmentCommentDao;
import ecoo.data.ShipmentComment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Repository(value = "shipmentCommentDao")
public class ShipmentCommentDaoImpl extends BaseAuditLogDaoImpl<Integer, ShipmentComment> implements ShipmentCommentDao {

    @Autowired
    public ShipmentCommentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, ShipmentComment.class);
    }
}
