package ecoo.dao.impl.hibernate;

import ecoo.dao.DocumentTypeDao;
import ecoo.data.DocumentType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Repository(value = "documentTypeDao")
public class DocumentTypeDaoImpl extends BaseAuditLogDaoImpl<String, DocumentType> implements DocumentTypeDao {

    @Autowired
    public DocumentTypeDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, DocumentType.class);
    }
}
