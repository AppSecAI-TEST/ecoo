package ecoo.service.impl;

import ecoo.dao.UserSignatureDao;
import ecoo.data.UserSignature;
import ecoo.service.UserSignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcUserSignatureServiceImpl extends JdbcAuditTemplate<Integer, UserSignature, UserSignatureDao>
        implements UserSignatureService {

    private UserSignatureDao userSignatureDao;

    @Autowired
    public JdbcUserSignatureServiceImpl(UserSignatureDao userSignatureDao) {
        super(userSignatureDao);
        this.userSignatureDao = userSignatureDao;
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
