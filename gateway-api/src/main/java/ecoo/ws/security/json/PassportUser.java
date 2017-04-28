package ecoo.ws.security.json;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.data.User;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PassportUser {

    /**
     * Needed for http requests after authentication.
     */
    @NotNull
    private final User user;

    @NotNull
    private String username;

    @NotNull
    private final Set<String> roles;

    public PassportUser(User user, Set<String> roles) {
        Assert.notNull(user);
        this.user = user;
        this.roles = roles;
        this.username = user.getUsername();
    }

    @JsonGetter
    public Integer getId() {
        return user.getPrimaryId();
    }

    @JsonGetter
    public String getUsername() {
        return username;
    }

    @JsonGetter
    public String getEmail() {
        return user.getPrimaryEmailAddress();
    }

    @JsonGetter
    public String getFirstName() {
        return user.getFirstName();
    }

    @JsonGetter
    public String getLastName() {
        return user.getLastName();
    }

    @JsonGetter
    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public User getUser() {
        return user;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
