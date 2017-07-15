package ecoo.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecoo.ws.security.json.PassportUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PassportUserToTokenConverter implements Converter<PassportUser, String> {

    private static final Logger LOG = LoggerFactory.getLogger(PassportUserToTokenConverter.class);

    private static final String USER = "user";

    private static final long TWO_DAY = 1000 * 60 * 60 * 24 * 2;

    private final String secret;

    public PassportUserToTokenConverter(String secret) {
        this.secret = secret;
    }

    @Override
    public String convert(PassportUser passportUser) {
        Assert.notNull(passportUser);
        try {
            final Map<String, Object> claims = new HashMap<>();

            final String json = new ObjectMapper().writeValueAsString(passportUser);
            claims.put(USER, json);

            return Jwts.builder()
                    .setIssuedAt(DateTime.now().toDate())
                    .setIssuer("ecoo")
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .setExpiration(createExpirationDate())
                    .compact();

        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Date createExpirationDate() {
        return new Date(new Date().getTime() + TWO_DAY);
    }
}
