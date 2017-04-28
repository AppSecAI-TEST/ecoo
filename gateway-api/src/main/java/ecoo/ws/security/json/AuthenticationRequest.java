package ecoo.ws.security.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class AuthenticationRequest {

    private String username;

    private String password;

    public AuthenticationRequest(@JsonProperty("username") String username
            , @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
