package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.User;
import ecoo.service.NotificationService;
import ecoo.service.ShipmentActivityGroupService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class SendShipmentApprovalNotificationTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(SendShipmentApprovalNotificationTask.class);

    private NotificationService notificationService;

    private ShipmentActivityGroupService shipmentActivityGroupService;

    private UserService userService;

    @Autowired
    public SendShipmentApprovalNotificationTask(NotificationService notificationService, ShipmentActivityGroupService shipmentActivityGroupService, UserService userService) {
        this.notificationService = notificationService;
        this.shipmentActivityGroupService = shipmentActivityGroupService;
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final MimeMessage message = notificationService.createShipmentNotification(shipment, "Approved");
        log.info("Attempting to send notification to: {}", Arrays.toString(message.getAllRecipients()));

        notificationService.send(message, false);

        addActivity(shipment, Arrays.toString(message.getAllRecipients()));
    }

    private void addActivity(Shipment shipment, String recipients) {
        final User approvedBy = userService.findById(shipment.getApprovedBy());
        shipmentActivityGroupService.recordActivity(approvedBy, DateTime.now(), shipment
                , "Electronic documents generated and digital signature applied."
                , "Notification sent to " + recipients);
    }
}