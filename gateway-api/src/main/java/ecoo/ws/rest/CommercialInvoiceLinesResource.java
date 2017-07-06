package ecoo.ws.rest;

import ecoo.data.CommercialInvoiceLine;
import ecoo.service.CommercialInvoiceLineService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/shipments/ci/lines")
public class CommercialInvoiceLinesResource extends BaseResource {

    private CommercialInvoiceLineService commercialInvoiceLineService;

    @Autowired
    public CommercialInvoiceLinesResource(CommercialInvoiceLineService commercialInvoiceLineService) {
        this.commercialInvoiceLineService = commercialInvoiceLineService;
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<CommercialInvoiceLine>> findByShipment(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(commercialInvoiceLineService.findByShipment(shipmentId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommercialInvoiceLine> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(commercialInvoiceLineService.findById(id));
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteByShipment(@PathVariable Integer shipmentId) {
        final List<CommercialInvoiceLine> data = commercialInvoiceLineService.findByShipment(shipmentId);
        commercialInvoiceLineService.deleteAll(data);

        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/saveAll", method = RequestMethod.POST)
    public ResponseEntity<Collection<CommercialInvoiceLine>> saveAll(@RequestBody List<CommercialInvoiceLine> amounts) {
        return ResponseEntity.ok(commercialInvoiceLineService.saveAll(amounts));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommercialInvoiceLine> save(@RequestBody CommercialInvoiceLine commercialInvoiceLine) {
        return ResponseEntity.ok(commercialInvoiceLineService.save(commercialInvoiceLine));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommercialInvoiceLine> permanentlyDelete(@PathVariable Integer id) {
        final CommercialInvoiceLine commercialInvoiceLine = commercialInvoiceLineService.findById(id);
        return ResponseEntity.ok(commercialInvoiceLineService.delete(commercialInvoiceLine));
    }
}