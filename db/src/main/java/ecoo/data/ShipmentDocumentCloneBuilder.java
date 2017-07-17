package ecoo.data;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentDocumentCloneBuilder {

    private ShipmentDocument shipmentDocument;

    private ShipmentDocumentCloneBuilder() {
    }

    public static ShipmentDocumentCloneBuilder aShipmentDocument() {
        return new ShipmentDocumentCloneBuilder();
    }

    public ShipmentDocumentCloneBuilder withShipmentDocument(ShipmentDocument shipmentDocument) {
        this.shipmentDocument = shipmentDocument;
        return this;
    }

    public ShipmentDocument clone(Integer shipmentId) {
        ShipmentDocument shipmentDocument = new ShipmentDocument();
        shipmentDocument.setPrimaryId(null);
        shipmentDocument.setShipmentId(shipmentId);
        shipmentDocument.setDocumentType(this.shipmentDocument.getDocumentType());
        shipmentDocument.setFileName(this.shipmentDocument.getFileName());
        shipmentDocument.setData(this.shipmentDocument.getData());
        shipmentDocument.setMimeType(this.shipmentDocument.getMimeType());
        shipmentDocument.setSizeInKb(this.shipmentDocument.getSizeInKb());
        shipmentDocument.setDateCreated(new Date());
        return shipmentDocument;
    }
}
