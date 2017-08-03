package ecoo.bpm.tasks.shipment.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Chamber;
import ecoo.data.ChamberGroupIdentityFactory;
import ecoo.data.Shipment;
import ecoo.data.User;
import ecoo.service.ChamberService;
import ecoo.service.ShipmentActivityGroupService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
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
public class NewShipmentRequestTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(NewShipmentRequestTaskAssignmentHandler.class);

    private UserService userService;

    private ChamberService chamberService;

    private ShipmentActivityGroupService shipmentActivityGroupService;

    @Autowired
    public NewShipmentRequestTaskAssignmentHandler(UserService userService, ChamberService chamberService, ShipmentActivityGroupService shipmentActivityGroupService) {
        this.userService = userService;
        this.chamberService = chamberService;
        this.shipmentActivityGroupService = shipmentActivityGroupService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        final NewShipmentRequest request = (NewShipmentRequest) delegateTask.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final String chamberGroupIdentity = ChamberGroupIdentityFactory.build(shipment.getChamberId());
        delegateTask.setAssignee(null);
        delegateTask.addCandidateGroup(chamberGroupIdentity);
        log.info("Task to approve shipment {} assigned to candidate group associated to chambers {} <{}>."
                , shipment.getPrimaryId(), shipment.getChamberId(), chamberGroupIdentity);

        recordActivity(shipment);
    }

    private void recordActivity(Shipment shipment) {
        final User requestedBy = userService.findById(shipment.getRequestedBy());
        final Chamber chamber = chamberService.findById(shipment.getChamberId());

        final String comment = "Submitted for approval to " + chamber.getName() + ".";
        shipmentActivityGroupService.recordActivity(requestedBy, DateTime.now(), shipment, comment);
    }
}
