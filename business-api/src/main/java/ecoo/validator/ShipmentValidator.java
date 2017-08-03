package ecoo.validator;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.data.*;
import ecoo.service.CertificateOfOriginService;
import ecoo.service.CommercialInvoiceService;
import ecoo.service.ShipmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static ecoo.data.ShipmentStatus.valueOfById;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Component
public class ShipmentValidator {

    private ShipmentService shipmentService;

    private CommercialInvoiceService commercialInvoiceService;

    private CertificateOfOriginService certificateOfOriginService;

    @Autowired
    public ShipmentValidator(ShipmentService shipmentService, CommercialInvoiceService commercialInvoiceService, CertificateOfOriginService certificateOfOriginService) {
        this.shipmentService = shipmentService;
        this.commercialInvoiceService = commercialInvoiceService;
        this.certificateOfOriginService = certificateOfOriginService;
    }

    public void validate(Shipment shipment) {
        validateShipment(shipment);
        validateCommercialInvoice(shipment);
        validateCertificateOfOrigin(shipment);
    }

    private void validateCertificateOfOrigin(Shipment shipment) {
        final CertificateOfOrigin certificateOfOrigin = certificateOfOriginService.findById(shipment.getPrimaryId());
        if (certificateOfOrigin == null) {
            throw new BusinessRuleViolationException(String.format("System cannot complete request. No certificate of " +
                    "origin information captured for shipment #%s. Please capture one or more lines.", shipment.getPrimaryId()));

        } else if (certificateOfOrigin.getLines() == null || certificateOfOrigin.getLines().isEmpty()) {
            throw new BusinessRuleViolationException(String.format("System cannot complete request. No certificate of " +
                    "origin information captured for shipment #%s. Please capture one or more lines.", shipment.getPrimaryId()));
        } else {
            for (CertificateOfOriginLine certificateOfOriginLine : certificateOfOrigin.getLines()) {
                final String origin = StringUtils.stripToNull(certificateOfOriginLine.getOrigin());
                if (StringUtils.isBlank(origin)) {
                    final int rowNumber = certificateOfOrigin.getLines().indexOf(certificateOfOriginLine) + 1;
                    throw new BusinessRuleViolationException(String.format("System cannot complete request. No Country " +
                            "Of Origin captured for line %s. Country Of Origin cannot be blank or empty, please capture " +
                            "a value and re-submit.", rowNumber));
                }
            }
        }
    }

    private void validateCommercialInvoice(Shipment shipment) {
        final CommercialInvoice commercialInvoice = commercialInvoiceService.findById(shipment.getPrimaryId());
        if (commercialInvoice == null) {
            throw new BusinessRuleViolationException(String.format("System cannot complete request. No commercial invoice " +
                    "information captured for shipment #%s. Please either upload a reference document or, alternatively, " +
                    "capture one or more lines.", shipment.getPrimaryId()));

        }
    }

    private void validateShipment(Shipment shipment) {
        Assert.notNull(shipment, "System cannot complete request. The variable shipment cannot be null.");

        final String exporterReference = shipment.getExporterReference();
        Assert.hasText(exporterReference, "System cannot complete request. " +
                "The exporter reference is required.");

        final Shipment otherShipment = shipmentService.findShipmentByExporterReference(exporterReference);
        if (otherShipment != null && (shipment.getPrimaryId() == null || !shipment.getPrimaryId().equals(otherShipment.getPrimaryId()))) {
            final ShipmentStatus shipmentStatus = valueOfById(otherShipment.getStatus());
            if (shipmentStatus != null) {
                switch (shipmentStatus) {
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
}
