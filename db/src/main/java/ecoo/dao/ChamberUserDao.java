package ecoo.dao;

import ecoo.data.ChamberUser;
import ecoo.data.ChamberUserListRow;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberUserDao extends AuditLogDao<Integer, ChamberUser> {

    /**
     * Returns users for a given chamber and member indicator.
     *
     * @param chamberId The chamber pk.
     * @return A list of users.
     */
    Collection<ChamberUserListRow> findChamberUserListRowsByChamber(Integer chamberId);

    ChamberUser findByChamberAndUser(Integer chamberId, Integer userId);

    List<ChamberUser> findByUser(Integer userId);
}
