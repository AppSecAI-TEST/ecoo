package ecoo.security;

import ecoo.data.Role;
import ecoo.data.User;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since August 2016
 */
public class GrantRoleConfirmation implements Serializable {

    private User user;

    private Role role;

    private String message;

    public GrantRoleConfirmation(User user, Role role, String message) {
        this.user = user;
        this.role = role;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GrantRoleConfirmation{" +
                "user=" + user +
                ", role=" + role +
                ", message='" + message + '\'' +
                '}';
    }
}
