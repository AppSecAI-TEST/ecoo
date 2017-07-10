package ecoo.dao;

import ecoo.data.Role;
import ecoo.data.User;
import ecoo.data.UserStatus;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface UserDao extends AuditLogDao<Integer, User> {

    /**
     * Returns the list of users for the given company.
     *
     * @param companyId The company pk to evaluate.
     * @return The list of users.
     */
    List<User> findUsersByCompany(Integer companyId);

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
     * Returns the user for the given username.
     *
     * @param username The username to evaluate.
     * @return The user or null.
     */
    User findByUsername(String username);

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
     * Returns the user for the given activation serial number.
     *
     * @param activationSerialNumber The unique serial number.
     * @return The user or null.
     */
    User findByActivationSerialNumber(String activationSerialNumber);
}
