package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.service.ShipmentService;
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
public class ApproveShipmentRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ApproveShipmentRequestTask.class);

    private ShipmentService shipmentService;

    @Autowired
    public ApproveShipmentRequestTask(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();
        shipment.setStatus(ShipmentStatus.ApprovedAndPendingPayment.id());
        shipmentService.save(shipment);
        log.info("Saving shipment... {}", shipment);
    }
}