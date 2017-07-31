package ecoo.dao.impl.hibernate;

import ecoo.dao.UserDocumentDao;
import ecoo.data.UserDocument;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Repository(value = "userDocumentDao")
public class UserDocumentDaoImpl extends BaseAuditLogDaoImpl<Integer, UserDocument> implements UserDocumentDao {

    @Autowired
    public UserDocumentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, UserDocument.class);
    }

    /**
     * Returns the user document for the user and the type.
     *
     * @param userId       The user id.
     * @param documentType The document type.
     * @return The user document or null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public UserDocument findByUserAndType(Integer userId, String documentType) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        Assert.hasText(documentType, "The variable documentType cannot be null.");
        final List<UserDocument> data = (List<UserDocument>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_DOCUMENT_BY_USER_AND_TYPE"
                        , new String[]{"userId", "documentType"}
                        , new Object[]{userId, documentType});
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }

    /**
     * Returns the list of user documents for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserDocument> findByUser(Integer userId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        return (List<UserDocument>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_DOCUMENTS_BY_USER"
                , "userId", userId);
    }
}
