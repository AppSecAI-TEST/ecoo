package ecoo.dao;

import ecoo.data.ShipmentActivityGroup;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ShipmentActivityGroupDao extends BaseDao<Integer, ShipmentActivityGroup> {

    /**
     * Method to delete all activities by shipment.
     *
     * @param shipmentId The pk of the shipment.
     */
    void deleteAllActivitiesByShipment(Integer shipmentId);
}
