package ecoo.ws.common.rest;

import ecoo.convert.SouthAfricanIdentityNumberDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/SAIDValidation")
public class SAIDValidationResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(SAIDValidationResource.class);

    @RequestMapping(value = "/idType/{idType}/idNumber/{idNumber}", method = RequestMethod.GET)
    public ResponseEntity<Date> parseDateOfBirthFromSouthAfricaId(@PathVariable String idType
            , @PathVariable String idNumber) throws ParseException {
        Assert.isTrue("RSA".equalsIgnoreCase(idType));

        final Date date = new SouthAfricanIdentityNumberDateConverter().convert(idNumber);
        logOutcome(idType, idNumber, date);

        return ResponseEntity.ok(date);

    }

    private void logOutcome(@PathVariable String idType, @PathVariable String idNumber, Date date) {
        final String dateAsString = new SimpleDateFormat("yyyy/MM/dd").format(date);
        LOG.info(String.format("{idType:%s,idNumber:%s} parsed to %s.", idType, idNumber, dateAsString));
    }
}
