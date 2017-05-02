package ecoo.dao;

import ecoo.data.ChamberUser;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberUserDao extends AuditLogDao<Integer, ChamberUser> {

    ChamberUser findByChamberAndUser(Integer chamberId, Integer userId);

    List<ChamberUser> findByUser(Integer userId);
}
