package ecoo.service;

import ecoo.data.Chamber;
import ecoo.data.ChamberUser;
import ecoo.data.User;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberUserService extends CrudService<Integer, ChamberUser>, AuditedModelAware<ChamberUser> {

    ChamberUser findByChamberAndUser(Integer chamberId, Integer userId);

    ChamberUser addAssociation(Chamber chamber, User user);
}
