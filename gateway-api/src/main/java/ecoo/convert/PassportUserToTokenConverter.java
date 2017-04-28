package ecoo.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ecoo.ws.security.json.PassportUser;
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

//    private static final long TWO_DAY = 1000 * 60 * 60 * 24 * 2;
    private static final long TWO_DAY = 60000;

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
            claims.put(Claims.EXPIRATION, createDefaultExpirationDate());

            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();

        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Date createDefaultExpirationDate() {
        return new Date(new Date().getTime() + TWO_DAY);
    }
}
