package ecoo.policy;

import org.springframework.util.StringUtils;

/**
 * A utility class that represents a password policy.
 *
 * @author Justin Rundle
 * @since September 2016
 */
public final class PasswordPolicy {

    /**
     * The minimum length of a password.
     */
    public static final int MINIMUM_LENGTH = 6;

    /**
     * The minimum length of a password.
     */
    public static final int MINIMUM_GENERATED_LENGTH = 10;

    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    public static final String MESSAGE_EMPTY_PASSWORD = "Password value required.";

    public static final String MESSAGE_PASSWORD_CONFIRMATION_EMPTY = "Confirmation password value required.";

    public static final String MESSAGE_PASSWORD_CONFIRMATION_NOT_EQUAL = "Password does not match confirmation value.";

    public static final String MESSAGE_PASSWORD_LENGTH = String.format("Password does not meet the minimum length of %s.", MINIMUM_LENGTH);

    public static final String MESSAGE_PASSWORD_REQUIREMENTS = "Password not done meet minimum requirements.";

    public static final String[] HINTS = new String[]{
            "Must contain at least 1 numeric value"
            , "Must contain at least 1 lower case character"
            , "Must contain at least 1 upper case character"
            , "Must contain at least 1 special character"
            , "Must contain no whitespaces"
            , String.format("Must be a minimum length of %s characters", MINIMUM_LENGTH)
    };

    /**
     * The standard name of the digest algorithm.
     */
    public static final String ALGORITHM = "MD5";

    /**
     * Default constructor.
     */
    public PasswordPolicy() {
    }

    /**
     * Method used to validate a password.
     *
     * @param password        The new password.
     * @param confirmPassword The confirmation of the new password.
     * @return The error message or null if no error.
     */
    public String validate(final String password, final String confirmPassword) {
        if (StringUtils.isEmpty(password)) {
            return MESSAGE_EMPTY_PASSWORD;

        } else if (StringUtils.isEmpty(confirmPassword)) {
            return MESSAGE_PASSWORD_CONFIRMATION_EMPTY;

        } else if (!StringUtils.isEmpty(password) && !password.equals(confirmPassword)) {
            return MESSAGE_PASSWORD_CONFIRMATION_NOT_EQUAL;

        } else if (password.trim().length() < MINIMUM_LENGTH) {
            return MESSAGE_PASSWORD_LENGTH;

        } else if (!password.matches(PASSWORD_PATTERN)) {
            return MESSAGE_PASSWORD_REQUIREMENTS;
        }
        return null;
    }


}
