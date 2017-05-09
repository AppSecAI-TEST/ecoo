package ecoo.ws.rest;

import ecoo.bpm.entity.ShipmentRequest;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.audit.Revision;
import ecoo.log.aspect.ProfileExecution;
import ecoo.service.ShipmentService;
import ecoo.ws.common.json.QueryPageRquestResponse;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseEntity<Shipment> submit(@RequestBody ShipmentRequest request) {
        Assert.notNull(request, "The variable shipment cannot be null.");
        // TODO: Needs more work
        request.getShipment().setStatus(ShipmentStatus.SubmittedAndPendingChamberApproval.id());
        return ResponseEntity.ok(shipmentService.save(request.getShipment()));
    }

    @RequestMapping(value = "/reopen", method = RequestMethod.POST)
    public ResponseEntity<Shipment> reopen(@RequestBody Shipment shipment) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");
        return ResponseEntity.ok(shipmentService.reopen(shipment));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<Shipment> cancel(@RequestBody Shipment shipment) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");
        return ResponseEntity.ok(shipmentService.cancel(shipment));
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
        final List<Shipment> data = new ArrayList<>(shipmentService
                .query(q, status, start, pageSize, column, direction));
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