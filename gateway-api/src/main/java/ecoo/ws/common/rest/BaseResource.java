package ecoo.ws.common.rest;

import ecoo.data.User;
import ecoo.security.UserAuthentication;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class BaseResource {

    protected final User currentUser() {
        final UserAuthentication authentication = (UserAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        final User currentUser = (User) authentication.getDetails();
        Assert.state(currentUser != null, "No current user defined in session");
        return currentUser;
    }


//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<String> handleBaseException(final Exception e, final HttpServletRequest request) {
//        return handleThrowable(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
