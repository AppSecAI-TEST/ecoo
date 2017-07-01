package ecoo.ws.user.rest;

import ecoo.data.ChamberRate;
import ecoo.data.audit.Revision;
import ecoo.service.ChamberRateService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/chambers/rates")
public class ChamberRateResource extends BaseResource {

    private ChamberRateService chamberRateService;

    @Autowired
    public ChamberRateResource(ChamberRateService chamberRateService) {
        this.chamberRateService = chamberRateService;
    }

    @RequestMapping(value = "/chamber/{chamberId}", method = RequestMethod.GET)
    public ResponseEntity<List<ChamberRate>> findRatesByChamber(@PathVariable Integer chamberId) {
        return ResponseEntity.ok(chamberRateService.findRatesByChamber(chamberId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ChamberRate> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chamberRateService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ChamberRate> save(@RequestBody ChamberRate entity) {
        return ResponseEntity.ok(chamberRateService.save(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final ChamberRate entity = chamberRateService.findById(id);
        return ResponseEntity.ok(chamberRateService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final ChamberRate entity = chamberRateService.findById(id);
        return ResponseEntity.ok(chamberRateService.findModifiedBy(entity));
    }
}