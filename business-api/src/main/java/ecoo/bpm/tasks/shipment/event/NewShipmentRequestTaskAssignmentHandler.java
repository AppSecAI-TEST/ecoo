package ecoo.bpm.tasks.shipment.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.*;
import ecoo.service.ChamberService;
import ecoo.service.ShipmentCommentService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
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
public class NewShipmentRequestTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(NewShipmentRequestTaskAssignmentHandler.class);

    private ShipmentCommentService shipmentCommentService;

    private UserService userService;

    private ChamberService chamberService;

    @Autowired
    public NewShipmentRequestTaskAssignmentHandler(ShipmentCommentService shipmentCommentService, UserService userService, ChamberService chamberService) {
        this.shipmentCommentService = shipmentCommentService;
        this.userService = userService;
        this.chamberService = chamberService;
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

        addComment(shipment);
    }

    private void addComment(Shipment shipment) {
        final User SystemAccount = userService.findById(KnownUser.SystemAccount.getPrimaryId());

        final Chamber chamber = chamberService.findById(shipment.getChamberId());

        final ShipmentComment shipmentComment = new ShipmentComment();
        shipmentComment.setShipmentId(shipment.getPrimaryId());
        shipmentComment.setUser(SystemAccount);
        shipmentComment.setText("SUBMITTED FOR APPROVAL TO " + chamber.getName());
        shipmentComment.setDateCreated(new Date());
        
        shipmentCommentService.save(shipmentComment);
        log.info("Saving comment... {}", shipment);
    }
}
