package ecoo.service.impl;

import ecoo.dao.ChamberDao;
import ecoo.data.Chamber;
import ecoo.data.ChamberUser;
import ecoo.data.Role;
import ecoo.data.User;
import ecoo.service.ChamberService;
import ecoo.service.ChamberUserService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class ChamberServiceImpl extends AuditTemplate<Integer, Chamber, ChamberDao>
        implements ChamberService {

    private ChamberDao chamberDao;

    private ChamberUserService chamberUserService;

    @Autowired
    public ChamberServiceImpl(ChamberDao chamberDao, ChamberUserService chamberUserService) {
        super(chamberDao);
        this.chamberDao = chamberDao;
        this.chamberUserService = chamberUserService;
    }

    /**
     * Returns a list of Chambers that the given user is associated to.
     *
     * @param user The user to evaluate.
     * @return The list of associated Chambers.
     */
    @Override
    public Collection<Chamber> findAssociatedChambersByUser(User user) {
        Assert.notNull(user, "The variable user cannot be null.");
        if (user.isInRole(Role.ROLE_SYSADMIN)) {
            return chamberDao.findAll();
        } else {
            final Collection<Chamber> chambers = new HashSet<>();
            for (ChamberUser chamberUser : chamberUserService.findByUserAndEffectiveDate(user.getPrimaryId(), DateTime.now())) {
                chambers.add(chamberUser.getChamber());
            }
            return chambers;
        }
    }
}
