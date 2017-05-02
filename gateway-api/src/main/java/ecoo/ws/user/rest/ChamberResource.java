package ecoo.ws.user.rest;

import ecoo.data.Chamber;
import ecoo.data.audit.Revision;
import ecoo.service.ChamberService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/chambers")
public class ChamberResource extends BaseResource {

    private ChamberService chamberService;

    @Autowired
    public ChamberResource(ChamberService chamberService) {
        this.chamberService = chamberService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<Collection<Chamber>> findAssociatedChambersByLoggedInUser() {
        return ResponseEntity.ok(chamberService.findAssociatedChambersByUser(currentUser()));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Chamber>> findAll() {
        return ResponseEntity.ok(chamberService.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Chamber> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chamberService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Chamber> save(@RequestBody Chamber entity) {
        return ResponseEntity.ok(chamberService.save(entity));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Chamber> delete(@PathVariable Integer id) {
        final Chamber entity = chamberService.findById(id);
        return ResponseEntity.ok(chamberService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final Chamber entity = chamberService.findById(id);
        return ResponseEntity.ok(chamberService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final Chamber entity = chamberService.findById(id);
        return ResponseEntity.ok(chamberService.findModifiedBy(entity));
    }
}