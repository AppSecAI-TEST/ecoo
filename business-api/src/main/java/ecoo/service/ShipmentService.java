package ecoo.service;

import ecoo.data.Shipment;
import ecoo.data.User;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface ShipmentService extends CrudService<Integer, Shipment>, AuditedModelAware<Shipment> {

    /**
     * Returns the shipment for the given exporter reference.
     *
     * @param exporterReference The exporter reference.
     * @return The shipment.
     */
    Shipment findShipmentByExporterReference(String exporterReference);

    /**
     * Returns the shipment for the given process instance id.
     *
     * @param processInstanceId The BPM process instance id.
     * @return The shipment or null.
     */
    Shipment findByProcessInstanceId(String processInstanceId);

    /**
     * Returns the count of the shipments associated to the given user.
     *
     * @param requestingUser The user asking to see the shipments.
     * @return The count.
     */
    Long countShipmentsAssociatedToUser(User requestingUser);

    /**
     * Returns a list of shipments.
     *
     * @param q              The query value.
     * @param status         The status to evaluate.
     * @param start          The start row index.
     * @param pageSize       The page size.
     * @param column         The sort column index.
     * @param direction      The direction to sort ASC or DESC.
     * @param requestingUser The user asking to see the shipments.
     * @return A list.
     */
    List<Shipment> queryShipmentsAssociatedToUser(String q, String status, Integer start, Integer pageSize, Integer column, String direction
            , User requestingUser);

    /**
     * The method used to re-open a shipment.
     *
     * @param shipment The shipment to re-open.
     */
    Shipment reopen(Shipment shipment);
}
