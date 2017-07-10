package ecoo.dao.impl.hibernate;

import ecoo.dao.UserSignatureDao;
import ecoo.data.UserSignature;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "userSignatureDao")
public class UserSignatureDaoImpl extends BaseAuditLogDaoImpl<Integer, UserSignature> implements UserSignatureDao {

    @Autowired
    public UserSignatureDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, UserSignature.class);
    }

    /**
     * Returns the user signature for the given user and effective date.
     *
     * @param userId        The user pk.
     * @param effectiveDate The date to evaluate.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public UserSignature findByUserAndEffectiveDate(Integer userId, DateTime effectiveDate) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        final List<UserSignature> data = (List<UserSignature>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam("FIND_USER_SIGNATURES_BY_USER_AND_EFFECTIVE_DATE"
                        , new String[]{"userId", "effectiveDate"}, new Object[]{userId, effectiveDate.toDate()});
        if (data == null || data.isEmpty()) return null;
        return data.iterator().next();
    }

    /**
     * Returns the list of user bank accounts for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserSignature> findByUser(Integer userId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        return (List<UserSignature>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_USER_SIGNATURES_BY_USER"
                , "userId", userId);

    }
}
