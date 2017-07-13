package ecoo.ws.system.rest;

import ecoo.data.Shipment;
import ecoo.data.User;
import ecoo.service.NotificationService;
import ecoo.service.ShipmentService;
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

    private NotificationService notificationService;

    private ShipmentService shipmentService;

@Autowired
    public NotificationResource(NotificationService notificationService, ShipmentService shipmentService) {
        this.notificationService = notificationService;
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/send/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> sendTestEmail(@PathVariable Integer shipmentId) throws UnsupportedEncodingException, AddressException {
        Shipment shipment = shipmentService.findById(shipmentId);

        final MimeMessage newUserConfirmationEmail = notificationService.createShipmentNotification(shipment,"TEST");
        notificationService.send(newUserConfirmationEmail);

        return ResponseEntity.ok(true);
    }
}
