package ecoo.validator;

import ecoo.dao.UserDao;
import ecoo.data.CommunicationPreferenceType;
import ecoo.data.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class UserValidator {

    private UserDao userDao;

    @Autowired
    public UserValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public void validate(User user) {
        Assert.notNull(user, "user cannot be null.");
        assertCommunicationPreferences(user);
        assertUniqueUsername(user);
        assertUniquePersonalReference(user);
    }

    private void assertCommunicationPreferences(User user) {
        if (StringUtils.isBlank(user.getPrimaryEmailAddress()) && StringUtils.isBlank(user.getMobileNumber())) {
            throw new DataIntegrityViolationException("System cannot complete request. Neither a primary " +
                    "email address nor the mobile number defined. Please ensure the user has at least one contact captured.");
        }

        if (user.isCommunicationPreferenceType(CommunicationPreferenceType.SMS) && StringUtils.isBlank(user.getMobileNumber())) {
            throw new DataIntegrityViolationException("System cannot complete request. The preferred communication type is set to SMS " +
                    "and no mobile number was captured.");
        }

        if (user.isCommunicationPreferenceType(CommunicationPreferenceType.EMAIL) && StringUtils.isBlank(user.getPrimaryEmailAddress())) {
            throw new DataIntegrityViolationException("System cannot complete request. The preferred communication type is set to EMAIL " +
                    "and no primary email address was captured.");
        }
    }

    private void assertUniqueUsername(User user) {
        User otherUser = userDao.findByUsername(user.getUsername());
        if ((otherUser != null && user.getPrimaryId() == null) || (otherUser != null && !otherUser.getPrimaryId().equals(user.getPrimaryId()))) {
            throw new DataIntegrityViolationException(String.format("System cannot complete request. Another user with the " +
                    "username %s already exists.", user.getUsername()));
        }
    }

    private void assertUniquePersonalReference(User user) {
        if (StringUtils.isNotBlank(user.getPersonalReferenceValue())) {
            User otherUser = userDao.findByPersonalReference(user.getPersonalReferenceType(), user.getPersonalReferenceValue());
            if ((otherUser != null && user.getPrimaryId() == null) || (otherUser != null && !otherUser.getPrimaryId().equals(user.getPrimaryId()))) {
                throw new DataIntegrityViolationException(String.format("System cannot complete request. Another user with the " +
                        "personal reference type %s and value %s already exists.", user.getPersonalReferenceType(), user.getPersonalReferenceValue()));
            }
        }
    }
}
