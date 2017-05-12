package ecoo.service;

import ecoo.data.ShipmentComment;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface ShipmentCommentService extends CrudService<Integer, ShipmentComment> {

    /**
     * Returns all the shipment comments for the give shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list of shipment related comments.
     */
    List<ShipmentComment> findByShipmentId(Integer shipmentId);

    /**
     * Method to recreate the ES index.
     */
    void recreateIndex();

}
