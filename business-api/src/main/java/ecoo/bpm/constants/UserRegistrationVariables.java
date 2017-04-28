package ecoo.bpm.constants;

/**
 * @author Justin Rundle
 * @since April 2016
 */
public enum UserRegistrationVariables {

    ADMIN_SOURCE("adminSource"), APP_HOME("applicationHome"), SOURCE_DIR("srcDir"), PLAIN_TEXT_PASSWORD("plainTextPassword");

    private String id;

    UserRegistrationVariables(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}