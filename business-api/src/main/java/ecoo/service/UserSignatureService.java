package ecoo.service;

import ecoo.data.Signature;
import ecoo.data.UserSignature;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UserSignatureService extends CrudService<Integer, UserSignature>, AuditedModelAware<UserSignature> {

    UserSignature assign(Integer userId, Signature signature);

    /**
     * Returns the list of user addresses for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    List<UserSignature> findByUser(Integer userId);
}
