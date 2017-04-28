package ecoo.dao;

import ecoo.data.UserSignature;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UserSignatureDao extends AuditLogDao<Integer, UserSignature> {

    /**
     * Returns the list of user bank accounts for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    List<UserSignature> findByUser(Integer userId);
}
