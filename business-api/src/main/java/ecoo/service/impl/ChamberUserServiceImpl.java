package ecoo.service.impl;

import ecoo.dao.ChamberUserDao;
import ecoo.data.Chamber;
import ecoo.data.ChamberUser;
import ecoo.data.User;
import ecoo.service.ChamberUserService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class ChamberUserServiceImpl extends AuditTemplate<Integer, ChamberUser, ChamberUserDao>
        implements ChamberUserService {

    private ChamberUserDao chamberUserDao;

    @Autowired
    public ChamberUserServiceImpl(ChamberUserDao chamberUserDao) {
        super(chamberUserDao);
        this.chamberUserDao = chamberUserDao;
    }

    @Override
    public ChamberUser findByChamberAndUser(Integer chamberId, Integer userId) {
        return chamberUserDao.findByChamberAndUser(chamberId, userId);
    }

    @Transactional
    @Override
    public ChamberUser addAssociation(Chamber chamber, User user) {
        Assert.notNull(chamber, "The variable chamber cannot be null.");
        Assert.notNull(user, "The variable user cannot be null.");

        final ChamberUser chamberUser = new ChamberUser();
        chamberUser.setChamber(chamber);
        chamberUser.setUserId(user.getPrimaryId());
        chamberUser.setStartDate(DateTime.now().toDate());
        chamberUser.setEndDate(DateTime.parse("99991231", DateTimeFormat.forPattern("yyyyMMdd")).toDate());

        return save(chamberUser);
    }

    @Override
    public Collection<ChamberUser> findByUser(Integer userId) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        return chamberUserDao.findByUser(userId);
    }

    @Override
    public Collection<ChamberUser> findByUserAndEffectiveDate(Integer userId, DateTime effectiveDate) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        Assert.notNull(effectiveDate, "The variable effectiveDate cannot be null.");
        final Collection<ChamberUser> chamberUsers = new HashSet<>();
        for (ChamberUser chamberUser : chamberUserDao.findByUser(userId)) {
            final DateTime startDate = DateTime.now().withMillis(chamberUser.getStartDate().getTime());
            final DateTime endDate = DateTime.now().withMillis(chamberUser.getEndDate().getTime());
            if (effectiveDate.isEqual(startDate) || (effectiveDate.isAfter(startDate) && effectiveDate.isBefore(endDate))) {
                chamberUsers.add(chamberUser);
            }
        }
        return chamberUsers;
    }
}
