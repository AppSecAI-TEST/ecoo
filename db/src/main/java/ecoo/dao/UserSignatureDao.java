package ecoo.dao;

import ecoo.data.UserSignature;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UserSignatureDao extends AuditLogDao<Integer, UserSignature> {

    /**
     * Returns the user signature for the given user and effective date.
     *
     * @param userId        The user pk.
     * @param effectiveDate The date to evaluate.
     * @return A list.
     */
    UserSignature findByUserAndEffectiveDate(Integer userId, DateTime effectiveDate);

    /**
     * Returns the list of user signatures for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    List<UserSignature> findByUser(Integer userId);
}
