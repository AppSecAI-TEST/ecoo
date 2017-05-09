package ecoo.bpm.tasks.shipment.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.ChamberGroupIdentityFactory;
import ecoo.data.Shipment;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class NewShipmentRequestTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(NewShipmentRequestTaskAssignmentHandler.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        final NewShipmentRequest request = (NewShipmentRequest) delegateTask.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final String chamberGroupIdentity = ChamberGroupIdentityFactory.build(shipment.getChamberId());
        delegateTask.addCandidateGroup(chamberGroupIdentity);
        log.info("Task to approve shipment {} assigned to candidate group associated to chambers {} <{}>."
                , shipment.getPrimaryId(), shipment.getChamberId(), chamberGroupIdentity);
    }
}
