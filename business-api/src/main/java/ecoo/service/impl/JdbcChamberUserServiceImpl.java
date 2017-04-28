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

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcChamberUserServiceImpl extends JdbcAuditTemplate<Integer, ChamberUser, ChamberUserDao>
        implements ChamberUserService {

    private ChamberUserDao chamberUserDao;

    @Autowired
    public JdbcChamberUserServiceImpl(ChamberUserDao chamberUserDao) {
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
}
