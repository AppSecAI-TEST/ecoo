package ecoo.ws.common.json;

import ecoo.data.Shipment;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class ShipmentCloneRequest {

    private Shipment shipment;

    private String newExporterReference;

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getNewExporterReference() {
        return newExporterReference;
    }

    public void setNewExporterReference(String newExporterReference) {
        this.newExporterReference = newExporterReference;
    }
}
