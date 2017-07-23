package ecoo.service;

import ecoo.data.ShipmentActivityGroup;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ShipmentActivityGroupService extends CrudService<Integer, ShipmentActivityGroup> {

    List<ShipmentActivityGroup> findShipmentActivityGroupsByShipmentId(Integer shipmentId);

    List<ShipmentActivityGroup> buildAllHistory(Integer shipmentId);
}
