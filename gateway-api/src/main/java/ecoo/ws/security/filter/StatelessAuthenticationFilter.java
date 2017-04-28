package ecoo.ws.security.filter;

import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.lang.Assert;
import ecoo.convert.HttpServletRequestToAuthenticationConverter;
import ecoo.security.UserAuthentication;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private final String secret;

    public StatelessAuthenticationFilter(final String secret) {
        Assert.hasText(secret);
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            final UserAuthentication authentication = new HttpServletRequestToAuthenticationConverter(secret)
                    .convert(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            applyUserToMDC(authentication);

            chain.doFilter(request, response); // always continue

        } catch (final SignatureException e) {
            // This exception is thrown if it is not a signed JWS or token cannot be converted because the
            // token has changed or been tampered with.
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private void applyUserToMDC(UserAuthentication authentication) {
        if (authentication != null && authentication.getDetails() != null) {
            final String username = authentication.getDetails().getUsername();
            MDC.put("username", username);
        }
    }
}