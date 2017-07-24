package ecoo.ws.rest;

import ecoo.data.ShipmentActivityGroup;
import ecoo.service.ShipmentActivityGroupService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/shipments/activities")
public class ShipmentActivityResource extends BaseResource {

    private ShipmentActivityGroupService shipmentActivityGroupService;

    @Autowired
    public ShipmentActivityResource(ShipmentActivityGroupService shipmentActivityGroupService) {
        this.shipmentActivityGroupService = shipmentActivityGroupService;
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ShipmentActivityGroup>> findShipmentActivityGroupsByShipmentId(@PathVariable Integer shipmentId) {
        return findShipmentActivityGroupsByShipmentId(shipmentId, "ASC");
    }

    @RequestMapping(value = "/shipment/{shipmentId}/order/{order}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ShipmentActivityGroup>> findShipmentActivityGroupsByShipmentId(@PathVariable Integer shipmentId
            , @PathVariable String order) {
        final List<ShipmentActivityGroup> groups = shipmentActivityGroupService.findShipmentActivityGroupsByShipmentId(shipmentId);
        if (order.equalsIgnoreCase("DESC")) {
            final Set<ShipmentActivityGroup> reverseOrder = new TreeSet<>((o1, o2) -> o2.getPrimaryId().compareTo(o1.getPrimaryId()));
            reverseOrder.addAll(groups);
            return ResponseEntity.ok(reverseOrder);

        } else {
            final Set<ShipmentActivityGroup> ascendingOrder = new TreeSet<>(Comparator.comparing(ShipmentActivityGroup::getPrimaryId));
            ascendingOrder.addAll(groups);
            return ResponseEntity.ok(ascendingOrder);
        }
    }

    @RequestMapping(value = "/shipment/{shipmentId}/build", method = RequestMethod.GET)
    public ResponseEntity<List<ShipmentActivityGroup>> buildHistoryWithStartOfShipment(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(shipmentActivityGroupService.buildHistoryWithStartOfShipment(shipmentId));
    }
}