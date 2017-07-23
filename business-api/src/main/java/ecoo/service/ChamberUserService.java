package ecoo.service;

import ecoo.data.Chamber;
import ecoo.data.ChamberUser;
import ecoo.data.ChamberUserListRow;
import ecoo.data.User;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberUserService extends CrudService<Integer, ChamberUser>
        , AuditedModelAware<Integer, ChamberUser> {

    /**
     * Returns users for a given chamber and member indicator.
     *
     * @param chamberId The chamber pk.
     * @return A list of users.
     */
    Collection<ChamberUserListRow> findChamberUserListRowsByChamber(Integer chamberId);

    ChamberUser findByChamberAndUser(Integer chamberId, Integer userId);

    ChamberUser addAssociation(Chamber chamber, User user, boolean member);

    Collection<ChamberUser> findByUser(Integer userId);

    Collection<ChamberUser> findByUserAndEffectiveDate(Integer userId, DateTime effectiveDate);

}
