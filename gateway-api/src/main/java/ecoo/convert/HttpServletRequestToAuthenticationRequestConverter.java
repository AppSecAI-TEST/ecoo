package ecoo.convert;

import com.google.gson.Gson;
import ecoo.ws.security.json.AuthenticationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class HttpServletRequestToAuthenticationRequestConverter implements Converter<HttpServletRequest, AuthenticationRequest> {

    private static final Logger  LOG = LoggerFactory.getLogger(HttpServletRequestToAuthenticationRequestConverter.class);

    @Override
    public AuthenticationRequest convert(final HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;

            final StringBuilder json = new StringBuilder();
            while ((line = br.readLine()) != null) {
                json.append(line);
            }

            if (json.toString().length() > 0) {
                return new Gson().fromJson(json.toString(), AuthenticationRequest.class);
            }
            return null;

        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.warn(e.getMessage(), e);
                }
            }
        }
    }
}
