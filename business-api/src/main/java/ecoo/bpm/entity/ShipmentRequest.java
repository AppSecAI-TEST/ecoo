package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;
import ecoo.data.Shipment;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class ShipmentRequest extends WorkflowRequest implements Serializable {

    private static final long serialVersionUID = -5636726554980365222L;

    private Shipment shipment;

    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.ShipmentRequest;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "ShipmentRequest{" +
                "shipment=" + shipment +
                '}';
    }
}