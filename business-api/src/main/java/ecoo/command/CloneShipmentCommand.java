package ecoo.command;

import ecoo.data.*;
import ecoo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class CloneShipmentCommand {

    private ShipmentService shipmentService;

    private CommercialInvoiceService commercialInvoiceService;

    private CommercialInvoiceLineService commercialInvoiceLineService;

    private CommercialInvoiceAmountService commercialInvoiceAmountService;

    private ShipmentDocumentService shipmentDocumentService;

    private CertificateOfOriginService certificateOfOriginService;

    private ShipmentCommentService shipmentCommentService;

    @Autowired
    public CloneShipmentCommand(ShipmentService shipmentService, CommercialInvoiceService commercialInvoiceService, CommercialInvoiceLineService commercialInvoiceLineService, CommercialInvoiceAmountService commercialInvoiceAmountService, ShipmentDocumentService shipmentDocumentService, CertificateOfOriginService certificateOfOriginService, ShipmentCommentService shipmentCommentService) {
        this.shipmentService = shipmentService;
        this.commercialInvoiceService = commercialInvoiceService;
        this.commercialInvoiceLineService = commercialInvoiceLineService;
        this.commercialInvoiceAmountService = commercialInvoiceAmountService;
        this.shipmentDocumentService = shipmentDocumentService;
        this.certificateOfOriginService = certificateOfOriginService;
        this.shipmentCommentService = shipmentCommentService;
    }

    @Transactional
    public Shipment execute(Shipment shipment, String newExporterReference, User requestedBy) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");
        Assert.notNull(requestedBy, "The variable requestedBy cannot be null.");

        final Integer shipmentId = shipment.getPrimaryId();
        Assert.notNull(shipmentId, "System cannot complete request. The system" +
                " cannot clone new shipments.");

        Shipment shipmentClone = cloneShipment(shipment, newExporterReference, requestedBy);

        final Integer shipmentCloneId = shipmentClone.getPrimaryId();

        cloneCommercialInvoice(shipmentId, shipmentCloneId);
        cloneCertificateOfOrigin(shipmentId, shipmentCloneId);
        addComment(shipmentId, shipmentCloneId, requestedBy);

        return shipmentClone;
    }

    private void addComment(Integer shipmentId, Integer shipmentCloneId, User requestedBy) {
        shipmentCommentService.addComment(shipmentCloneId, requestedBy
                , String.format("SHIPMENT CLONED FROM SHIPMENT #%s BY %s.", shipmentId, requestedBy.getDisplayName()));
    }

    private Shipment cloneShipment(Shipment shipment, String newExporterReference, User requestedBy) {
        final Shipment shipmentClone = ShipmentCloneBuilder.aShipment()
                .withShipment(shipment)
                .clone(requestedBy, newExporterReference);
        return shipmentService.save(shipmentClone);
    }

    private void cloneCommercialInvoice(Integer shipmentId, Integer shipmentCloneId) {
        final CommercialInvoice commercialInvoice = commercialInvoiceService.findById(shipmentId);
        if (commercialInvoice != null) {
            final CommercialInvoice commercialInvoiceClone = CommercialInvoiceCloneBuilder.aCommercialInvoice()
                    .withCommercialInvoice(commercialInvoice)
                    .clone(shipmentCloneId);
            commercialInvoiceService.save(commercialInvoiceClone);

            for (CommercialInvoiceLine commercialInvoiceLine : commercialInvoiceLineService.findByShipment(shipmentId)) {
                final CommercialInvoiceLine commercialInvoiceLineClone = CommercialInvoiceLineCloneBuilder.aCommercialInvoiceLine()
                        .withCommercialInvoiceLine(commercialInvoiceLine)
                        .clone(shipmentCloneId);
                commercialInvoiceLineService.save(commercialInvoiceLineClone);
            }

            for (CommercialInvoiceAmount commercialInvoiceAmount : commercialInvoiceAmountService.findByShipment(shipmentId)) {
                final CommercialInvoiceAmount commercialInvoiceAmountClone = CommercialInvoiceAmountCloneBuilder.aCommercialInvoiceAmount()
                        .withCommercialInvoiceAmount(commercialInvoiceAmount)
                        .clone(shipmentCloneId);
                commercialInvoiceAmountService.save(commercialInvoiceAmountClone);
            }

            for (ShipmentDocument shipmentDocument : shipmentDocumentService.findByShipment(shipmentId)) {
                final ShipmentDocument shipmentDocumentClone = ShipmentDocumentCloneBuilder.aShipmentDocument()
                        .withShipmentDocument(shipmentDocument)
                        .clone(shipmentCloneId);
                shipmentDocumentService.save(shipmentDocumentClone);
            }
        }
    }

    private void cloneCertificateOfOrigin(Integer shipmentId, Integer shipmentCloneId) {
        final CertificateOfOrigin certificateOfOrigin = certificateOfOriginService.findById(shipmentId);
        if (certificateOfOrigin != null) {
            final CertificateOfOrigin certificateOfOriginClone = CertificateOfOriginCloneBuilder.aCertificateOfOrigin()
                    .withCertificateOfOrigin(certificateOfOrigin)
                    .clone(shipmentCloneId);
            certificateOfOriginService.save(certificateOfOriginClone);
        }
    }
}
