package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ecoo.data.Shipment;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class NewShipmentResponse implements Serializable {

    private Shipment shipment;

    private String processInstanceId;

    @JsonCreator
    public NewShipmentResponse(@JsonProperty("shipment") Shipment shipment
            , @JsonProperty("processInstanceId") String processInstanceId) {
        this.shipment = shipment;
        this.processInstanceId = processInstanceId;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public String toString() {
        return "NewShipmentResponse{" +
                "shipment=" + shipment +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}