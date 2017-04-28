package ecoo.ws.system.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/exceptions")
public class ExceptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionResource.class);

    @RequestMapping(value = "", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> reportClientSideException(@RequestBody Object exception) {
        // {errorUrl=http://localhost:8888/#/dashboard, errorMessage=ReferenceError: y is not defined}
        LOG.error(exception.toString());
        return ResponseEntity.ok(true);
    }
}
