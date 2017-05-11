package ecoo.ws.rest;

import ecoo.data.ShipmentComment;
import ecoo.service.ShipmentCommentService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@RestController
@RequestMapping("/api/shipments/comments")
public class ShipmentCommentResource extends BaseResource {

    private ShipmentCommentService shipmentCommentService;

    @Autowired
    public ShipmentCommentResource(ShipmentCommentService shipmentCommentService) {
        this.shipmentCommentService = shipmentCommentService;
    }

    @RequestMapping(value = "/shipmentId/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<List<ShipmentComment>> findByShipmentId(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(shipmentCommentService.findByShipmentId(shipmentId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ShipmentComment> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(shipmentCommentService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ShipmentComment> save(@RequestBody ShipmentComment shipmentComment) {
        return ResponseEntity.ok(shipmentCommentService.save(shipmentComment));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<ShipmentComment> delete(@RequestBody ShipmentComment shipmentComment) {
        return ResponseEntity.ok(shipmentCommentService.delete(shipmentComment));
    }
}