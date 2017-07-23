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

import java.util.List;

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
    public ResponseEntity<List<ShipmentActivityGroup>> findShipmentActivityGroupsByShipmentId(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(shipmentActivityGroupService.findShipmentActivityGroupsByShipmentId(shipmentId));
    }

    @RequestMapping(value = "/shipment/{shipmentId}/build", method = RequestMethod.GET)
    public ResponseEntity<List<ShipmentActivityGroup>> build(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(shipmentActivityGroupService.buildAllHistory(shipmentId));
    }
}