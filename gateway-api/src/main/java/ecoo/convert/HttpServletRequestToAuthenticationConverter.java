package ecoo.convert;

import io.jsonwebtoken.lang.Assert;
import ecoo.security.UserAuthentication;
import ecoo.ws.security.json.PassportUser;
import org.springframework.core.convert.converter.Converter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class HttpServletRequestToAuthenticationConverter implements Converter<HttpServletRequest, UserAuthentication> {

    private final String secret;

    public HttpServletRequestToAuthenticationConverter(String secret) {
        Assert.hasText(secret);
        this.secret = secret;
    }

    @Override
    public UserAuthentication convert(final HttpServletRequest request) {
        final String token = request.getHeader("authorization");
        if (token != null) {
            final PassportUser passportUser = new TokenToPassportUserConverter(secret)
                    .convert(token);
            if (passportUser != null) {
                return new UserAuthentication(passportUser.getUser());
            }
        }
        return null;
    }
}
