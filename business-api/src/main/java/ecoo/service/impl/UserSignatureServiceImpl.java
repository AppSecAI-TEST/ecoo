package ecoo.service.impl;

import ecoo.dao.UserSignatureDao;
import ecoo.data.Signature;
import ecoo.data.UserSignature;
import ecoo.service.SignatureService;
import ecoo.service.UserSignatureService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class UserSignatureServiceImpl extends AuditTemplate<Integer, UserSignature, UserSignatureDao>
        implements UserSignatureService {

    private UserSignatureDao userSignatureDao;

    private SignatureService signatureService;

    @Autowired
    public UserSignatureServiceImpl(UserSignatureDao dao, UserSignatureDao userSignatureDao, SignatureService signatureService) {
        super(dao);
        this.userSignatureDao = userSignatureDao;
        this.signatureService = signatureService;
    }

    @Transactional
    @Override
    public UserSignature assign(Integer userId, Signature signature) {
        Assert.notNull(userId, "The variable userId cannot be null.");
        Assert.notNull(signature, "The variable signature cannot be null.");

        final DateTime startDate = DateTime.now();
        final UserSignature userSignature = userSignatureDao.findByUserAndEffectiveDate(userId, startDate);
        if (userSignature != null) {
            userSignature.setEffectiveTo(startDate.toDate());
        }

        final UserSignature newUserSignature = new UserSignature();
        newUserSignature.setUserId(userId);
        newUserSignature.setEncodedImage(signature.getEncodedImage());
        newUserSignature.setEffectiveFrom(startDate.toDate());
        newUserSignature.setEffectiveTo(DateTime.parse("99991231", DateTimeFormat.forPattern("yyyyMMdd")).toDate());
        userSignatureDao.save(newUserSignature);

        signature.setUserSignatureId(newUserSignature.getPrimaryId());
        signatureService.save(signature);

        signatureService.delete(signature);
        return newUserSignature;
    }

    /**
     * Returns the list of user addresses for the given user.
     *
     * @param userId The user pk.
     * @return A list.
     */
    @Override
    public List<UserSignature> findByUser(Integer userId) {
        return userSignatureDao.findByUser(userId);
    }
}
