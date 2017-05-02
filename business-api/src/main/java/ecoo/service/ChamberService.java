package ecoo.service;

import ecoo.data.Chamber;
import ecoo.data.User;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberService extends CrudService<Integer, Chamber>, AuditedModelAware<Chamber> {

    /**
     * Returns a list of Chambers that the given user is associated to.
     *
     * @param user The user to evaluate.
     * @return The list of associated Chambers.
     */
    Collection<Chamber> findAssociatedChambersByUser(User user);

}
