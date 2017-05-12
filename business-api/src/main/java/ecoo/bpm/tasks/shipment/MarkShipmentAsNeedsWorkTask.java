package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentComment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentCommentService;
import ecoo.service.ShipmentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class MarkShipmentAsNeedsWorkTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(MarkShipmentAsNeedsWorkTask.class);

    private ShipmentService shipmentService;

    private ShipmentCommentService shipmentCommentService;

    @Autowired
    public MarkShipmentAsNeedsWorkTask(ShipmentService shipmentService, ShipmentCommentService shipmentCommentService) {
        this.shipmentService = shipmentService;
        this.shipmentCommentService = shipmentCommentService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");
        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();
        shipment.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        saveShipment(shipment);

        final String comment = (String) delegateExecution.getVariable("comment");
        addComment(shipment, request.getRequestingUser(), comment);
    }

    private void saveShipment(Shipment shipment) {
        shipment.setStatus(ShipmentStatus.NewAndPendingSubmission.id());
        shipmentService.save(shipment);
        log.info("Saving shipment... {}", shipment);
    }

    private void addComment(Shipment shipment, User assignee, String comment) {
        final ShipmentComment shipmentComment = new ShipmentComment();
        shipmentComment.setShipmentId(shipment.getPrimaryId());
        shipmentComment.setUser(assignee);
        shipmentComment.setText(comment);
        shipmentComment.setDateCreated(new Date());
        shipmentCommentService.save(shipmentComment);
        log.info("Saving comment... {}", shipment);
    }
}