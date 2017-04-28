package ecoo.service;

import ecoo.data.Role;
import ecoo.data.User;
import ecoo.data.UserStatus;
import ecoo.security.GrantRoleConfirmation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UserService extends CrudService<Integer, User>, UserDetailsService, AuditedModelAware<User> {

    /**
     * Returns a collection users associated to the given role.
     *
     * @param role The role to evaluate.
     * @return A collection of users.
     */
    Collection<User> findByRole(Role role);

    /**
     * Returns all users in the given status.
     *
     * @param status The status to evaluate.
     * @return A collection of users.
     */
    Collection<User> findByStatus(UserStatus status);

    /**
     * Returns the count of all users in the given status.
     *
     * @param status The status to evaluate.
     * @return The count.
     */
    long countByStatus(UserStatus status);

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
    User setPassword(User user, String plainTextPassword) throws NoSuchAlgorithmException, UnsupportedEncodingException;

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
    User setPassword(User user, String plainTextPassword, boolean passwordExpired) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    /**
     * Returns the user for the given personal reference.
     *
     * @param type  The personal reference type.
     * @param value The personal reference value.
     * @return The user or null.
     */
    User findByPersonalReference(String type, String value);

    /**
     * Returns the user for the given primary email address.
     *
     * @param primaryEmailAddress The primary email address.
     * @return The user or null.
     */
    User findByPrimaryEmailAddress(String primaryEmailAddress);

    /**
     * Returns the user for the given mobile number.
     *
     * @param mobileNumber The mobile number.
     * @return The user or null.
     */
    User findByMobileNumber(String mobileNumber);


    /**
     * Method used to lock a user's account.
     *
     * @param user the user whom's account to lock
     */
    User disableAccount(User user);

    /**
     * Method used to unlock a user's account.
     *
     * @param user the user whom's account to unlock
     */
    User enableAccount(User user);


    /**
     * Returns the user for the given username.
     *
     * @param username The username to evaluate.
     * @return The user or null.
     */
    User findByUsername(String username);

    /**
     * Method to grant a role to the given user.
     *
     * @param user The user to evaluate.
     * @param role The role to grant.
     * @return The updated user.
     */
    GrantRoleConfirmation grant(User user, Role role);

    /**
     * Method to revoke a role from the given user.
     *
     * @param user The user to evaluate.
     * @param role The role to revoke.
     * @return The updated user.
     */
    User revoke(User user, Role role);

    /**
     * Method to revoke a role from the given user.
     *
     * @param user     The user to evaluate.
     * @param roleName The role to revoke.
     * @return The updated user.
     */
    User revoke(User user, String roleName);

    /**
     * Method to authenticate the given user with the given password.
     *
     * @param username The username.
     * @param password The secret password.
     * @return The authentication model.
     */
    Authentication authenticate(String username, String password);
}
