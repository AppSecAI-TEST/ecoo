package ecoo.ws.rest;

import ecoo.command.GenerateCooPdfCommand;
import ecoo.data.CommercialInvoice;
import ecoo.data.Shipment;
import ecoo.data.audit.Revision;
import ecoo.service.CommercialInvoiceService;
import ecoo.service.ShipmentService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/shipments/ci")
public class CommercialInvoiceResource extends BaseResource {

    private CommercialInvoiceService commercialInvoiceService;

    private ShipmentService shipmentService;

    private GenerateCooPdfCommand generateCooPdfCommand;

    @Autowired
    public CommercialInvoiceResource(CommercialInvoiceService commercialInvoiceService, ShipmentService shipmentService, GenerateCooPdfCommand generateCooPdfCommand) {
        this.commercialInvoiceService = commercialInvoiceService;
        this.shipmentService = shipmentService;
        this.generateCooPdfCommand = generateCooPdfCommand;
    }

    @RequestMapping(value = "/shipment/{shipmentId}/generate", method = RequestMethod.GET)
    public ResponseEntity<String> generateCooPdf(@PathVariable Integer shipmentId) throws IOException {
        final Shipment shipment = shipmentService.findById(shipmentId);
        final File pdf = generateCooPdfCommand.execute(shipment);
        return ResponseEntity.ok(pdf.getAbsolutePath());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommercialInvoice> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(commercialInvoiceService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommercialInvoice> save(@RequestBody CommercialInvoice commercialInvoice) {
        return ResponseEntity.ok(commercialInvoiceService.save(commercialInvoice));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CommercialInvoice> permanentlyDelete(@PathVariable Integer id) {
        final CommercialInvoice commercialInvoice = commercialInvoiceService.findById(id);
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