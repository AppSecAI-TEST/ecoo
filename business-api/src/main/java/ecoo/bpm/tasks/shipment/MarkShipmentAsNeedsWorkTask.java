package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentActivityGroupService;
import ecoo.service.ShipmentCommentService;
import ecoo.service.ShipmentService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private UserService userService;

    private ShipmentActivityGroupService shipmentActivityGroupService;

    @Autowired
    public MarkShipmentAsNeedsWorkTask(ShipmentService shipmentService, ShipmentCommentService shipmentCommentService, UserService userService, ShipmentActivityGroupService shipmentActivityGroupService) {
        this.shipmentService = shipmentService;
        this.shipmentCommentService = shipmentCommentService;
        this.userService = userService;
        this.shipmentActivityGroupService = shipmentActivityGroupService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");
        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();
        shipment.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        saveShipment(shipment);

        final Integer actionedById = (Integer) delegateExecution.getVariable("actionedBy");
        final User actionedBy = userService.findById(actionedById);

        final String comment = (String) delegateExecution.getVariable("comment");
        addComment(shipment, actionedBy, comment);
    }

    private void saveShipment(Shipment shipment) {
        shipment.setStatus(ShipmentStatus.NewAndPendingSubmission.id());
        shipmentService.save(shipment);
        log.info("Saving shipment... {}", shipment);
    }

    private void addComment(Shipment shipment, User assignee, String comment) {
        shipmentCommentService.addComment(shipment.getPrimaryId(), assignee, comment);

        shipmentActivityGroupService.recordActivity(assignee, DateTime.now(), shipment
                , "Comment added.", comment);
    }
}