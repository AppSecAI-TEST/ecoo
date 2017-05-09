package ecoo.bpm.tasks.shipment;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class ValidateShipmentRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ValidateShipmentRequestTask.class);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");
        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();
        if (!shipment.isInStatus(ShipmentStatus.NewAndPendingSubmission)) {
            final ShipmentStatus shipmentStatus = ShipmentStatus.valueOfById(shipment.getStatus());
            throw new BusinessRuleViolationException(String.format("System cannot complete request. Shipment %s is in " +
                    "status %s and cannot be submitted for approval.", shipment.getPrimaryId(), shipmentStatus));
        }

        // DEVNOTE: Check unique on the export reference?
    }
}