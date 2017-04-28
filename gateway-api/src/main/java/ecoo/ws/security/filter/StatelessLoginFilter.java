package ecoo.ws.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecoo.convert.HttpServletRequestToAuthenticationRequestConverter;
import ecoo.convert.PassportUserToTokenConverter;
import ecoo.convert.UserDetailsToPassportUserConverter;
import ecoo.security.UserAuthentication;
import ecoo.service.UserService;
import ecoo.ws.security.json.AuthenticationRequest;
import ecoo.ws.security.json.PassportUser;
import ecoo.ws.security.jwt.JWTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final String secret;

    private final UserService userService;

    private CounterService counterService;

    public StatelessLoginFilter(String urlMapping
            , String secret
            , UserService userService
            , CounterService counterService
            , AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping));
        this.secret = secret;
        this.userService = userService;
        this.counterService = counterService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final AuthenticationRequest authenticationRequest = new HttpServletRequestToAuthenticationRequestConverter()
                .convert(request);
        if (authenticationRequest == null) {
            return null;
        } else {
            final Authentication authentication = userService.authenticate(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword());
            if (authentication == null) {
                counterService.increment("counter.login.failure");
                return null;
            } else {
                counterService.increment("counter.login.success");
                return authentication;
            }
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response
            , FilterChain chain, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");

        // Lookup the complete User object from the database and create an Authentication for it
        final UserDetails userDetails = userService.loadUserByUsername(authentication.getName());

        // Add the custom token as HTTP header to the response
        final PassportUser passportUser = new UserDetailsToPassportUserConverter().convert(userDetails);
        final UserAuthentication userAuthentication = new UserAuthentication(userDetails);
        final String token = new PassportUserToTokenConverter(secret).convert(passportUser);
        response.addHeader("authorization", token);

        final String json = new ObjectMapper().writeValueAsString(new JWTToken(passportUser, token));

        PrintWriter out = response.getWriter();
//        LOG.info(json);

        // Assuming your json object is **jsonObject**, perform the following, it will return your json object
        out.print(json);
        out.flush();

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}