package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentCommentService;
import ecoo.service.ShipmentService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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
public class RejectShipmentRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(RejectShipmentRequestTask.class);

    private ShipmentService shipmentService;

    private ShipmentCommentService shipmentCommentService;

    private UserService userService;

    @Autowired
    public RejectShipmentRequestTask(ShipmentService shipmentService, ShipmentCommentService shipmentCommentService, UserService userService) {
        this.shipmentService = shipmentService;
        this.shipmentCommentService = shipmentCommentService;
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Integer actionedBy = (Integer) delegateExecution.getVariable("actionedBy");

        final Shipment shipment = request.getShipment();
        shipment.setStatus(ShipmentStatus.Declined.id());
        shipmentService.save(shipment);
        log.info("Saving shipment... {}", shipment);

        addComment(actionedBy, shipment);
    }

    private void addComment(Integer actionedBy, Shipment shipment) {
        final User user = userService.findById(actionedBy);
        shipmentCommentService.addComment(shipment, user, "Shipment declined");
    }
}