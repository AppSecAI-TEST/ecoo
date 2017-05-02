package ecoo.service;

import ecoo.data.Chamber;
import ecoo.data.ChamberUser;
import ecoo.data.User;
import org.joda.time.DateTime;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface ChamberUserService extends CrudService<Integer, ChamberUser>, AuditedModelAware<ChamberUser> {

    ChamberUser findByChamberAndUser(Integer chamberId, Integer userId);

    ChamberUser addAssociation(Chamber chamber, User user);

    Collection<ChamberUser> findByUser(Integer userId);

    Collection<ChamberUser> findByUserAndEffectiveDate(Integer userId, DateTime effectiveDate);
}
