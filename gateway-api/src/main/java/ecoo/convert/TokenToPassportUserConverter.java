package ecoo.convert;

import com.google.gson.Gson;
import ecoo.ws.security.json.PassportUser;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
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
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(token)
                    .getBody();

            final String json = claims.get(USER).toString();
            if (json == null) {
                return null;
            }

            final DateTime expirationDate = DateTime.now().withMillis(claims.getExpiration().getTime());
            if (expirationDate.isBefore(DateTime.now())) {
                throw new ExpiredJwtException("System cannot completed request. Credentials have expired, " +
                        "please login in again.");
            }

            return new Gson().fromJson(json, PassportUser.class);

        } catch (final MalformedJwtException e) {
            log.warn(e.getMessage(), e);
            throw new SignatureException("System cannot completed request. JWT token rejected, either the token is " +
                    "for the correct application and environment or taken intended for another application or state and " +
                    "expired token.");
        }
    }
}
