package ecoo.ws.system.rest;

import ecoo.data.User;
import ecoo.service.NotificationService;
import ecoo.service.UserService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationResource extends BaseResource {

    private UserService userService;

    private NotificationService notificationService;

    @Autowired
    public NotificationResource(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/send/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> sendTestEmail(@PathVariable Integer userId) throws UnsupportedEncodingException, AddressException {
        final User recipient = userService.findById(userId);
        
        final MimeMessage newUserConfirmationEmail = notificationService.createNewUserConfirmationEmail(recipient,null);
        notificationService.send(newUserConfirmationEmail);

        return ResponseEntity.ok(true);
    }
}
