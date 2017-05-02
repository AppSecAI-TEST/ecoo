package ecoo.dao.impl.hibernate;

import ecoo.dao.UploadStatusDao;
import ecoo.data.upload.UploadStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unchecked")
@Repository(value = "uploadStatusDao")
public class UploadStatusDaoImpl extends BaseHibernateDaoImpl<Integer, UploadStatus> implements UploadStatusDao {

    /**
     * Constructs a new {@link UploadStatusDaoImpl} data access object.
     */
    @Autowired
    public UploadStatusDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, UploadStatus.class);
    }
}
