package ecoo.ws.rest;

import ecoo.data.Shipment;
import ecoo.data.audit.Revision;
import ecoo.log.aspect.ProfileExecution;
import ecoo.service.ShipmentService;
import ecoo.ws.common.json.QueryPageRquestResponse;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@RestController
@RequestMapping("/api/shipments")
public class ShipmentResource extends BaseResource {

    private ShipmentService shipmentService;

    @Autowired
    public ShipmentResource(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/size"
            , method = RequestMethod.GET)
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(shipmentService.count());
    }

    @ProfileExecution
    @RequestMapping(value = "/q/status/{status}/start/{start}/pageSize/{pageSize}/column/{column}" +
            "/direction/{direction}/totalRecords/{totalRecords}"
            , method = RequestMethod.GET)
    public ResponseEntity<QueryPageRquestResponse> query(@PathVariable String status
            , @PathVariable Integer start
            , @PathVariable Integer pageSize
            , @PathVariable Integer column
            , @PathVariable String direction
            , @PathVariable Integer totalRecords) {
        return query(null, status, start, pageSize, column, direction, totalRecords);
    }

    @ProfileExecution
    @RequestMapping(value = "/q/{q}/status/{status}/start/{start}/pageSize/{pageSize}/column/{column}" +
            "/direction/{direction}/totalRecords/{totalRecords}"
            , method = RequestMethod.GET)
    public ResponseEntity<QueryPageRquestResponse> query(@PathVariable String q
            , @PathVariable String status
            , @PathVariable Integer start
            , @PathVariable Integer pageSize
            , @PathVariable Integer column
            , @PathVariable String direction
            , @PathVariable Integer totalRecords) {
        final List<Shipment> data = shipmentService.query(q, status, start, pageSize, column, direction)
                .stream().collect(Collectors.toList());
        return ResponseEntity.ok(new QueryPageRquestResponse(totalRecords, totalRecords, data));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Shipment>> findAll() {
        return ResponseEntity.ok(shipmentService.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Shipment> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(shipmentService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Shipment> save(@RequestBody Shipment shipment) {
        return ResponseEntity.ok(shipmentService.save(shipment));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Shipment> delete(@RequestBody Shipment shipment) {
        return ResponseEntity.ok(shipmentService.delete(shipment));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final Shipment entity = shipmentService.findById(id);
        return ResponseEntity.ok(shipmentService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final Shipment entity = shipmentService.findById(id);
        return ResponseEntity.ok(shipmentService.findModifiedBy(entity));
    }
}