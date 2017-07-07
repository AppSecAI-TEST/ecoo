package ecoo.bpm.tasks.shipment.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.User;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class ShipmentRequestNeedsWorkdTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(ShipmentRequestNeedsWorkdTaskAssignmentHandler.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        final NewShipmentRequest request = (NewShipmentRequest) delegateTask.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final User requestingUser = request.getRequestingUser();
        delegateTask.setAssignee(requestingUser.getUsername());
        log.info("Shipment {} needs work and assigned to {} <{}>.", shipment.getPrimaryId()
                , requestingUser.getDisplayName(), requestingUser.getUsername());
    }
}
