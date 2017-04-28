package ecoo.dao.impl.hibernate;

import ecoo.dao.UploadTypeDao;
import ecoo.data.upload.UploadType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unchecked")
@Repository(value = "uploadTypeDao")
public class UploadTypeDaoImpl extends BaseHibernateDaoImpl<String, UploadType> implements UploadTypeDao {

    /**
     * Constructs a new {@link UploadTypeDaoImpl} data access object.
     */
    @Autowired
    public UploadTypeDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, UploadType.class);
    }
}
