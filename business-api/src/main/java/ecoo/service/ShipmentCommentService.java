package ecoo.service;

import ecoo.data.ShipmentComment;
import ecoo.data.User;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface ShipmentCommentService extends CrudService<Integer, ShipmentComment> {

    ShipmentComment addComment(Integer shipmentId, User user, String text);

    /**
     * Returns all the shipment comments for the give shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list of shipment related comments.
     */
    List<ShipmentComment> findByShipmentId(Integer shipmentId);
}
