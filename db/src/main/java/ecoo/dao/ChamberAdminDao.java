package ecoo.dao;

import ecoo.data.ChamberAdmin;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberAdminDao extends AuditLogDao<Integer, ChamberAdmin> {

    List<ChamberAdmin> findByUser(Integer userId);

    ChamberAdmin findByUserAndChamber(Integer userId, Integer chamberId);
}
