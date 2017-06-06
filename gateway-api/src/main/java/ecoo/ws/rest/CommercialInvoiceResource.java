package ecoo.ws.rest;

import ecoo.data.CommercialInvoice;
import ecoo.data.audit.Revision;
import ecoo.service.CommercialInvoiceService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/shipments/ci")
public class CommercialInvoiceResource extends BaseResource {

    private CommercialInvoiceService commercialInvoiceService;

    @Autowired
    public CommercialInvoiceResource(CommercialInvoiceService commercialInvoiceService) {
        this.commercialInvoiceService = commercialInvoiceService;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommercialInvoice> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(commercialInvoiceService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommercialInvoice> save(@RequestBody CommercialInvoice commercialInvoice) {
        return ResponseEntity.ok(commercialInvoiceService.save(commercialInvoice));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CommercialInvoice> delete(@RequestBody CommercialInvoice commercialInvoice) {
        return ResponseEntity.ok(commercialInvoiceService.delete(commercialInvoice));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final CommercialInvoice entity = commercialInvoiceService.findById(id);
        return ResponseEntity.ok(commercialInvoiceService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final CommercialInvoice entity = commercialInvoiceService.findById(id);
        return ResponseEntity.ok(commercialInvoiceService.findModifiedBy(entity));
    }
}