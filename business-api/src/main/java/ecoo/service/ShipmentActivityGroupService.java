package ecoo.service;

import ecoo.data.Shipment;
import ecoo.data.ShipmentActivityGroup;
import ecoo.data.User;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ShipmentActivityGroupService extends CrudService<Integer, ShipmentActivityGroup> {

    ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Shipment shipment);

    ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Shipment shipment, String...description);

    ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Integer shipmentId, String...description);

    List<ShipmentActivityGroup> findShipmentActivityGroupsByShipmentId(Integer shipmentId);

    List<ShipmentActivityGroup> buildHistoryWithStartOfShipment(Integer shipmentId);
}
