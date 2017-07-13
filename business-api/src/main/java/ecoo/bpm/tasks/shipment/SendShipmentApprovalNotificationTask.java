package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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

    @Autowired
    public SendShipmentApprovalNotificationTask(NotificationService notificationService) {
        this.notificationService = notificationService;
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
    }
}