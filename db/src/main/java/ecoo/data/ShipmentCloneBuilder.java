package ecoo.data;

import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentCloneBuilder {

    private Shipment shipment;

    private ShipmentCloneBuilder() {
    }

    public static ShipmentCloneBuilder aShipment() {
        return new ShipmentCloneBuilder();
    }

    public ShipmentCloneBuilder withShipment(Shipment shipment) {
        this.shipment = shipment;
        return this;
    }

    public Shipment clone(User requestedBy, String newExporterReference) {
        Assert.notNull(requestedBy, "The variable requestedBy cannot be null.");
        Assert.notNull(shipment, "The variable shipment cannot be null.");
        Assert.hasText(newExporterReference, "The variable newExporterReference cannot be null or blank.");

        Shipment clonedShipment = new Shipment();
        clonedShipment.setPrimaryId(null);
        clonedShipment.setChamberId(shipment.getChamberId());
        clonedShipment.setExporterReference(newExporterReference);
        clonedShipment.setExportInvoiceNumber(shipment.getExportInvoiceNumber());
        clonedShipment.setExportInvoiceDate(shipment.getExportInvoiceDate());
        clonedShipment.setBuyerReference(shipment.getBuyerReference());
        clonedShipment.setBuyerOrderDate(shipment.getBuyerOrderDate());
        clonedShipment.setLetterOfCreditNumber(shipment.getLetterOfCreditNumber());
        clonedShipment.setUcrNumber(shipment.getUcrNumber());
        clonedShipment.setExporterName(shipment.getExporterName());
        clonedShipment.setExporterBuilding(shipment.getExporterBuilding());
        clonedShipment.setExporterStreet(shipment.getExporterStreet());
        clonedShipment.setExporterCity(shipment.getExporterCity());
        clonedShipment.setExporterPostcode(shipment.getExporterPostcode());
        clonedShipment.setExporterProvince(shipment.getExporterProvince());
        clonedShipment.setExporterCountry(shipment.getExporterCountry());
        clonedShipment.setConsigneeName(shipment.getConsigneeName());
        clonedShipment.setConsigneeBuilding(shipment.getConsigneeBuilding());
        clonedShipment.setConsigneeStreet(shipment.getConsigneeStreet());
        clonedShipment.setConsigneeCity(shipment.getConsigneeCity());
        clonedShipment.setConsigneePostcode(shipment.getConsigneePostcode());
        clonedShipment.setConsigneeProvince(shipment.getConsigneeProvince());
        clonedShipment.setConsigneeCountry(shipment.getConsigneeCountry());
        clonedShipment.setBuyerName(shipment.getBuyerName());
        clonedShipment.setBuyerBuilding(shipment.getBuyerBuilding());
        clonedShipment.setBuyerStreet(shipment.getBuyerStreet());
        clonedShipment.setBuyerCity(shipment.getBuyerCity());
        clonedShipment.setBuyerPostcode(shipment.getBuyerPostcode());
        clonedShipment.setBuyerProvince(shipment.getBuyerProvince());
        clonedShipment.setBuyerCountry(shipment.getBuyerCountry());
        clonedShipment.setPlaceOfIssue(shipment.getPlaceOfIssue());
        clonedShipment.setDateOfIssue(new Date());
        clonedShipment.setDateSubmitted(new Date());
        clonedShipment.setStatus(ShipmentStatus.NewAndPendingSubmission.id());
        clonedShipment.setOwnerId(requestedBy.getPrimaryId());
        clonedShipment.setProcessInstanceId(null);
        clonedShipment.setTransportTypeId(shipment.getTransportTypeId());
        clonedShipment.setPortOfLoading(shipment.getPortOfLoading());
        clonedShipment.setPortOfAcceptance(shipment.getPortOfAcceptance());
        clonedShipment.setCurrency(shipment.getCurrency());
        clonedShipment.setRequestedBy(requestedBy.getPrimaryId());
        clonedShipment.setDateRequested(new Date());
        clonedShipment.setApprovedBy(null);
        clonedShipment.setDateApproved(null);
        return clonedShipment;
    }
}
