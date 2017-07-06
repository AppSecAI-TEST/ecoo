package ecoo.ws.rest;

import ecoo.data.CommercialInvoiceAmount;
import ecoo.service.CommercialInvoiceAmountService;
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

    private CommercialInvoiceAmountService commercialInvoiceAmountService;

    @Autowired
    public CommercialInvoiceLinesResource(CommercialInvoiceAmountService commercialInvoiceAmountService) {
        this.commercialInvoiceAmountService = commercialInvoiceAmountService;
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<CommercialInvoiceAmount>> findByShipment(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(commercialInvoiceAmountService.findByShipment(shipmentId));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Collection<CommercialInvoiceAmount>> saveAll(@RequestBody List<CommercialInvoiceAmount> amounts) {
        return ResponseEntity.ok(commercialInvoiceAmountService.saveAll(amounts));
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.POST)
    public ResponseEntity<Collection<CommercialInvoiceAmount>> deleteByShipment(@PathVariable Integer shipmentId) {
        final List<CommercialInvoiceAmount> amounts = commercialInvoiceAmountService.findByShipment(shipmentId);
        return ResponseEntity.ok(commercialInvoiceAmountService.deleteAll(amounts));
    }
}