package ecoo.validator;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Component
public class ShipmentValidator {

    private ShipmentService shipmentService;

    @Autowired
    public ShipmentValidator(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    public void validate(Shipment shipment) {
        Assert.notNull(shipment, "System cannot complete request. The variable shipment cannot be null.");

        final String exporterReference = shipment.getExporterReference();
        Assert.hasText(exporterReference, "System cannot complete request. " +
                "The exporter reference is required.");

        final Shipment otherShipment = shipmentService.findShipmentByExporterReference(exporterReference);
        if (otherShipment != null && (shipment.getPrimaryId() == null || !shipment.getPrimaryId().equals(otherShipment.getPrimaryId()))) {
            switch (ShipmentStatus.valueOfById(otherShipment.getStatus())) {
                case NewAndPendingSubmission:
                case SubmittedAndPendingChamberApproval:
                case Approved:
                    throw new BusinessRuleViolationException(String.format("System cannot complete request. Exporter " +
                            "reference must be unique where reference %s already associated to shipment #%s. Please check your " +
                            "exporter reference is correct and either change the value or contact your chamber if you feel " +
                            "this is in error.", exporterReference, otherShipment.getPrimaryId()));
                default:
            }
        }
    }
}
