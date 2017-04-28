package ecoo.ws.security.rest;

import ecoo.data.User;
import ecoo.security.UserAuthentication;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
public class SecurityResource extends BaseResource {

    @RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
    public UserDetails getCurrent() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            return ((UserAuthentication) authentication).getDetails();
        }
        return new User(authentication.getName()); //anonymous user support
    }
}
