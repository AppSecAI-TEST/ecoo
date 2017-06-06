package ecoo.service.impl;

import ecoo.dao.UserDao;
import ecoo.dao.impl.es.UserElasticsearchRepository;
import ecoo.data.Role;
import ecoo.data.User;
import ecoo.data.UserRole;
import ecoo.data.UserStatus;
import ecoo.policy.PasswordPolicy;
import ecoo.security.GrantRoleConfirmation;
import ecoo.security.UserAuthentication;
import ecoo.service.UserService;
import ecoo.util.HashGenerator;
import ecoo.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service(value = "userService")
public class UserServiceImpl extends ElasticsearchAuditTemplate<Integer
        , User
        , UserDao
        , UserElasticsearchRepository> implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    private UserValidator userValidator;

    private UserElasticsearchRepository userElasticsearchRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Qualifier("userElasticsearchRepository") UserElasticsearchRepository userElasticsearchRepository
            , UserValidator userValidator
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(userDao, userElasticsearchRepository, elasticsearchTemplate);
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.userElasticsearchRepository = userElasticsearchRepository;
    }

    /**
     * Returns the list of users for the given company.
     *
     * @param companyId The company pk to evaluate.
     * @return The list of users.
     */
    @Override
    public List<User> findUsersByCompany(Integer companyId) {
        return userDao.findUsersByCompany(companyId);
    }

    /**
     * Returns a collection users associated to the given role.
     *
     * @param role The role to evaluate.
     * @return A collection of users.
     */
    @Override
    public Collection<User> findByRole(Role role) {
        return userDao.findByRole(role);
    }

    /**
     * Returns all users in the given status.
     *
     * @param status The status to evaluate.
     * @return A collection of users.
     */
    @Override
    public Collection<User> findByStatus(UserStatus status) {
        return userElasticsearchRepository.findUsersByStatus(status.id());
    }

    /**
     * Returns the count of all users in the given status.
     *
     * @param status The status to evaluate.
     * @return The count.
     */
    @Override
    public long countByStatus(UserStatus status) {
        return findByStatus(status).size();
    }

    /**
     * Method used to set the password for the given user and password. The method will use the
     * plain text password, digest the password to get a hash value and set the hash value to the
     * user.
     *
     * @param user              The target user to set the password for.
     * @param plainTextPassword The new password.
     * @return The user with the new password set.
     * @throws NoSuchAlgorithmException If no Provider supports a MessageDigestSpi implementation
     *                                  for the specified algorithm.
     */
    @Override
    public User setPassword(User user, String plainTextPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return setPassword(user, plainTextPassword, true);
    }

    /**
     * Method used to set the password for the given user and password. The method will use the
     * plain text password, digest the password to get a hash value and set the hash value to the
     * user.
     *
     * @param user              The target user to set the password for.
     * @param plainTextPassword The new password.
     * @param passwordExpired   Force password to expire.
     * @return The user with the new password set.
     * @throws NoSuchAlgorithmException If no Provider supports a MessageDigestSpi implementation
     *                                  for the specified algorithm.
     */
    @Override
    public User setPassword(User user, String plainTextPassword, boolean passwordExpired) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Assert.notNull(user);
        Assert.hasText(plainTextPassword);

        // Generate the hash value of plain text password using the "MD5" algorithm.
        final String hashValue = new HashGenerator().digest(PasswordPolicy.ALGORITHM, plainTextPassword);
        if (LOG.isTraceEnabled()) {
            LOG.trace(String.format("Password %s digested to %s.", plainTextPassword, hashValue));
        }

        user.eraseCredentials();
        user.setPassword(hashValue);
        user.setPasswordExpired(passwordExpired);
        return user;
    }

    /**
     * Returns the user for the given personal reference.
     *
     * @param type  The personal reference type.
     * @param value The personal reference value.
     * @return The user or null.
     */
    @Override
    public User findByPersonalReference(String type, String value) {
        final List<User> users = userElasticsearchRepository.findUsersByPersonalReferenceTypeAndPersonalReferenceValue(
                type, value);
        if (users == null || users.isEmpty()) {
            final User user = userDao.findByPersonalReference(type, value);
            if (user == null) {
                return null;
            } else {
                userElasticsearchRepository.save(user);
                return user;
            }
        }
        return users.iterator().next();
    }

    /**
     * Returns the user for the given primary email address.
     *
     * @param primaryEmailAddress The primary email address.
     * @return The user or null.
     */
    @Override
    public User findByPrimaryEmailAddress(String primaryEmailAddress) {
        final List<User> users = userElasticsearchRepository.findUsersByPrimaryEmailAddress(primaryEmailAddress);
        if (users == null || users.isEmpty()) {
            final User user = userDao.findByPrimaryEmailAddress(primaryEmailAddress);
            if (user == null) {
                return null;
            } else {
                userElasticsearchRepository.save(user);
                return user;
            }
        }
        return users.iterator().next();
    }

    /**
     * Returns the user for the given mobile number.
     *
     * @param mobileNumber The mobile number.
     * @return The user or null.
     */
    @Override
    public User findByMobileNumber(String mobileNumber) {
        final List<User> users = userElasticsearchRepository.findUsersByMobileNumber(mobileNumber);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.iterator().next();
    }

    /**
     * Method used to lock a user's account.
     *
     * @param user the user whom's account to lock
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User disableAccount(User user) {
        user.setEnabled(false);
        userDao.save(user);
        userElasticsearchRepository.save(user);

        return userDao.findByPrimaryId(user.getPrimaryId());
    }

    /**
     * Method used to unlock a user's account.
     *
     * @param user the user whom's account to unlock
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User enableAccount(User user) {
        user.setEnabled(true);
        userDao.save(user);
        userElasticsearchRepository.save(user);

        return userDao.findByPrimaryId(user.getPrimaryId());
    }

    /**
     * Returns the user for the given username.
     *
     * @param username The username to evaluate.
     * @return The user or null.
     */
    @Override
    public User findByUsername(String username) {
        final List<User> users = userElasticsearchRepository.findUsersByUsername(username);
        if (users == null || users.isEmpty()) {
            final User user = userDao.findByUsername(username);
            if (user == null) {
                return null;
            } else {
                userElasticsearchRepository.save(user);
                return user;
            }
        }
        return users.iterator().next();
    }

    /**
     * Method to grant a role to the given user.
     *
     * @param user The user to evaluate.
     * @param role The role to grant.
     * @return The updated user.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public GrantRoleConfirmation grant(User user, Role role) {
        Assert.notNull(user);
        Assert.notNull(role);

        if (user.hasRole(role)) {
            throw new DataIntegrityViolationException(String.format("System cannot grant role as the user %s is already " +
                    "associated to the role %s.", user.getDisplayName(), role.name()));
        }

        if (user.hasRole(Role.ROLE_SYSADMIN)) {
            return new GrantRoleConfirmation(user, role, String.format("Role %s not granted to %s. As the user already " +
                    "is a SYSTEM ADMINISTRATOR", role.name(), user.getDisplayName()));
        }

        if (role.equals(Role.ROLE_SYSADMIN)) {
            final Collection<String> revokedRoles = new ArrayList<>();
            for (final UserRole userRole : new HashSet<>(user.getUserRoles())) {
                revokedRoles.add(userRole.getRole());
                user.getUserRoles().remove(userRole);
            }
            user.getUserRoles().add(new UserRole(user.getPrimaryId(), role.name()));
            user = save(user);

            if (revokedRoles.isEmpty()) {
                return new GrantRoleConfirmation(user, role, String.format("Role %s successfully granted to user %s.",
                        role.name(), user.getDisplayName()));
            } else {
                return new GrantRoleConfirmation(user, role, String.format("Role %s successfully granted to user %s and " +
                        "removed unnecessary roles %s.", role.name(), user.getDisplayName(), revokedRoles));
            }

        } else {
            user.getUserRoles().add(new UserRole(user.getPrimaryId(), role.name()));
            user = save(user);
            return new GrantRoleConfirmation(user, role, String.format("Role %s successfully granted to user %s.",
                    role.name(), user.getDisplayName()));
        }
    }

    /**
     * Method to revoke a role from the given user.
     *
     * @param user     The user to evaluate.
     * @param roleName The role to revoke.
     * @return The updated user.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User revoke(User user, String roleName) {
        return revoke(user, Role.valueOf(roleName));
    }

    /**
     * Method to revoke a role from the given user.
     *
     * @param user The user to evaluate.
     * @param role The role to revoke.
     * @return The updated user.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User revoke(User user, Role role) {
        Assert.notNull(user);
        Assert.notNull(role);

        for (final UserRole userRole : new HashSet<>(user.getUserRoles())) {
            if (userRole.getRole().equals(role.name())) {
                user.getUserRoles().remove(userRole);
                break;
            }
        }
        return save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = findByUsername(username);
        if (user == null) {
            return null;
        }

        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        detailsChecker.check(user);

        return user;
    }

    /**
     * Method to authenticate the given user with the given password.
     *
     * @param username The username.
     * @param password The secret password.
     * @return The authentication model.
     */
    @Override
    public Authentication authenticate(String username, String password) {
        LOG.info("Attempting to authenticate {}...", username);

        UserDetails userDetails = loadUserByUsername(username);
        if (userDetails != null) {
            if (userDetails.getPassword().equals(password)) {
                return new UserAuthentication(userDetails);
            } else {
                throw new BadCredentialsException("System cannot completed request. Invalid username and/or password.");
            }
        } else {
            userDetails = findByPrimaryEmailAddress(username);
            if (userDetails != null && userDetails.getPassword().equals(password)) {
                return new UserAuthentication(userDetails);
            } else {
                throw new BadCredentialsException("System cannot completed request. Invalid username and/or password.");
            }
        }
    }

    /**
     * Method called before save is called.
     *
     * @param user The user to save.
     */
    @Override
    protected void beforeSave(User user) {
        userValidator.validate(capitalize(user));
    }

    private User capitalize(User user) {
        user.setFirstName(StringUtils.upperCase(user.getFirstName()));
        user.setLastName(StringUtils.upperCase(user.getLastName()));

        if (user.getDisplayName() == null) {
            user.setDisplayName(StringUtils.upperCase(user.getFirstName() + " " + user.getLastName()));
        } else {
            user.setDisplayName(StringUtils.upperCase(user.getDisplayName()));
        }
        user.setPersonalReferenceValue(StringUtils.upperCase(user.getPersonalReferenceValue()));
        return user;
    }

    /**
     * Method called before save is called.
     *
     * @param user The entity to save.
     */
    @Override
    protected void beforeDelete(User user) {
//        final List<Claim> claims = claimService.findClaimsByUser(user.getPrimaryId());
//        LOG.info("Found {} claims for the user {}.", claims.size(), user.getDisplayName());
//
//        if (!claims.isEmpty()) {
//            throw new DataIntegrityViolationException(String.format("System cannot delete the user. The user %s is " +
//                    "associated %s claims and therefore cannot be deleted.", user.getDisplayName(), claims.size()));
//        }
    }
}
