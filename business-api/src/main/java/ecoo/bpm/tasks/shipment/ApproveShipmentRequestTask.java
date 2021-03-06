package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentActivityGroupService;
import ecoo.service.ShipmentService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
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
public class ApproveShipmentRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ApproveShipmentRequestTask.class);

    private ShipmentService shipmentService;

    private ShipmentActivityGroupService shipmentActivityGroupService;

    private UserService userService;

    @Autowired
    public ApproveShipmentRequestTask(ShipmentService shipmentService, ShipmentActivityGroupService shipmentActivityGroupService
            , UserService userService) {
        this.shipmentService = shipmentService;
        this.shipmentActivityGroupService = shipmentActivityGroupService;
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Integer approvedBy = (Integer) delegateExecution.getVariable("actionedBy");

        final Shipment shipment = request.getShipment();
        shipment.setStatus(ShipmentStatus.Approved.id());

        shipment.setApprovedBy(approvedBy);
        shipment.setDateApproved(new Date());

        shipmentService.save(shipment);
        log.info("Saving shipment... {}", shipment);

        recordActivity(approvedBy, shipment);
    }

    private void recordActivity(Integer approvedBy, Shipment shipment) {
        final User user = userService.findById(approvedBy);
        shipmentActivityGroupService.recordActivity(user, DateTime.now(), shipment.getPrimaryId()
                , "Shipment approved.");
    }
}