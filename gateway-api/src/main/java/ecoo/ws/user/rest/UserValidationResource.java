package ecoo.ws.user.rest;

import ecoo.data.User;
import ecoo.service.UserService;
import ecoo.validator.SouthAfricanIdentityNumberValidator;
import ecoo.validator.ValidationFailedException;
import ecoo.ws.common.json.ValidationResponse;
import ecoo.ws.common.rest.BaseResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/users/validate")
public class UserValidationResource extends BaseResource {

    private UserService userService;

    @Autowired
    public UserValidationResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public ResponseEntity<ValidationResponse> validateUsername(@RequestParam("id") Integer id, @RequestParam("username") String username) {
        username = StringUtils.trimToNull(username);
        if (StringUtils.isNotBlank(username)) {
            final User user = (User) userService.loadUserByUsername(username);
            if (user != null && !user.getPrimaryId().equals(id)) {
                return ResponseEntity.ok(new ValidationResponse(false, "Value is not unique"));
            } else {
                return ResponseEntity.ok(new ValidationResponse(true));
            }
        } else {
            return ResponseEntity.ok(new ValidationResponse(true));
        }
    }

    @RequestMapping(value = "/primaryEmailAddress", method = RequestMethod.GET)
    public ResponseEntity<ValidationResponse> validatePrimaryEmailAddress(@RequestParam("id") Integer id
            , @RequestParam("primaryEmailAddress") String primaryEmailAddress) {
        primaryEmailAddress = StringUtils.trimToNull(primaryEmailAddress);
        if (StringUtils.isNotBlank(primaryEmailAddress)) {
            final User user = userService.findByPrimaryEmailAddress(primaryEmailAddress);
            if (user != null && !user.getPrimaryId().equals(id)) {
                return ResponseEntity.ok(new ValidationResponse(false, "Value is not unique"));
            } else {
                return ResponseEntity.ok(new ValidationResponse(true));
            }
        } else {
            return ResponseEntity.ok(new ValidationResponse(true));
        }
    }

    @RequestMapping(value = "/mobileNumber", method = RequestMethod.GET)
    public ResponseEntity<ValidationResponse> validateMobileNumber(@RequestParam("id") Integer id
            , @RequestParam("mobileNumber") String mobileNumber) {
        mobileNumber = StringUtils.trimToNull(mobileNumber);
        if (StringUtils.isNotBlank(mobileNumber)) {
            final User user = userService.findByMobileNumber(mobileNumber);
            if (user != null && !user.getPrimaryId().equals(id)) {
                return ResponseEntity.ok(new ValidationResponse(false, "Value is not unique"));
            } else {
                return ResponseEntity.ok(new ValidationResponse(true));
            }
        } else {
            return ResponseEntity.ok(new ValidationResponse(true));
        }
    }

    @RequestMapping(value = "/personalReferenceValue", method = RequestMethod.GET)
    public ResponseEntity<ValidationResponse> validate(@RequestParam("id") Integer id
            , @RequestParam("personalReferenceType") String personalReferenceType
            , @RequestParam("personalReferenceValue") String personalReferenceValue) {
        personalReferenceType = StringUtils.trimToNull(personalReferenceType);
        personalReferenceValue = StringUtils.trimToEmpty(personalReferenceValue);
        if (personalReferenceType != null && personalReferenceType.equalsIgnoreCase("RSA")) {
            try {
                final SouthAfricanIdentityNumberValidator validator = new SouthAfricanIdentityNumberValidator();
                validator.validate(personalReferenceValue);
            } catch (final ValidationFailedException e) {
                return ResponseEntity.ok(new ValidationResponse(false, e.getMessage()));
            }
        }

        final User user = userService.findByPersonalReference(personalReferenceType, personalReferenceValue);
        if (user != null && !user.getPrimaryId().equals(id)) {
            return ResponseEntity.ok(new ValidationResponse(false, "Value is not unique"));
        } else {
            return ResponseEntity.ok(new ValidationResponse(true));
        }
    }
}