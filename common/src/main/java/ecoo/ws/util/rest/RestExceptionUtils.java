package ecoo.ws.util.rest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Justin Rundle
 * @since February 2017
 */
public class RestExceptionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionUtils.class.getSimpleName());

    public static ResponseEntity<String> handleThrowable(Throwable e, HttpServletRequest request, HttpStatus status) {
        final String msg = e.getMessage() + " (Request URL: " + request.getRequestURL() + ")";
        LOG.error(msg, e);
        return ResponseEntity.status(status).contentType(MediaType.TEXT_PLAIN)
                .header("ErrorMessage", new String[]{msg})
                .header("Url", new String[]{request.getRequestURL().toString()})
                .body(msg + "\n\n" + ExceptionUtils.getStackTrace(e));
    }
}
