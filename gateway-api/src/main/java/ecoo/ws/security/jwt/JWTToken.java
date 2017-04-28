package ecoo.ws.security.jwt;


import ecoo.ws.security.json.PassportUser;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class JWTToken {

    private PassportUser user;
    private String token;

    public JWTToken(PassportUser user, String token) {
        Assert.notNull(user);
        Assert.hasText(token);
        this.user = user;
        this.token = token;
    }

    public PassportUser getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
