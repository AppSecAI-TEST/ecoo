package ecoo.convert;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import ecoo.ws.security.json.PassportUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.Assert;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Justin Rundle
 * @since April 2017
 */
final class TokenToPassportUserConverter implements Converter<String, PassportUser> {

    private final Logger log = LoggerFactory.getLogger(TokenToPassportUserConverter.class);

    private static final String USER = "user";

    private final String secret;

    public TokenToPassportUserConverter(String secret) {
        this.secret = secret;
    }

    @Override
    public PassportUser convert(String token) {
        Assert.hasText(token);
        // LOG.info(token);

        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token)
                    .getBody();
            final String json = claims.get(USER).toString();
            //LOG.info(json);
            if (json == null) {
                return null;
            }
            return new Gson().fromJson(json, PassportUser.class);

        } catch (final MalformedJwtException e) {
            log.warn(e.getMessage(), e);
            throw new BadCredentialsException("System cannot completed request. JWT token rejected, either the token is " +
                    "for the correct application and environment or taken intended for another application or state and " +
                    "expired token.");
        }
    }
}
