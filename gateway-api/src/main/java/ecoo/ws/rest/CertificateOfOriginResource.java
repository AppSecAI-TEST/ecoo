package ecoo.ws.rest;

import ecoo.data.CertificateOfOrigin;
import ecoo.data.audit.Revision;
import ecoo.service.CertificateOfOriginService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/shipments/coo")
public class CertificateOfOriginResource extends BaseResource {

    private CertificateOfOriginService certificateOfOriginService;

    @Autowired
    public CertificateOfOriginResource(CertificateOfOriginService certificateOfOriginService) {
        this.certificateOfOriginService = certificateOfOriginService;
    }

    @RequestMapping(value = "/shipment/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<List<CertificateOfOrigin>> findCertificateOfOriginsByShipmentId(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(certificateOfOriginService.findCertificateOfOriginsByShipmentId(shipmentId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CertificateOfOrigin> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(certificateOfOriginService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Collection<CertificateOfOrigin>> saveAll(@RequestBody Collection<CertificateOfOrigin> certificateOfOrigins) {
        return ResponseEntity.ok(certificateOfOriginService.saveAll(certificateOfOrigins));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CertificateOfOrigin> delete(@PathVariable Integer id) {
        final CertificateOfOrigin certificateOfOrigin = certificateOfOriginService.findById(id);
        return ResponseEntity.ok(certificateOfOriginService.delete(certificateOfOrigin));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final CertificateOfOrigin entity = certificateOfOriginService.findById(id);
        return ResponseEntity.ok(certificateOfOriginService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final CertificateOfOrigin entity = certificateOfOriginService.findById(id);
        return ResponseEntity.ok(certificateOfOriginService.findModifiedBy(entity));
    }
}