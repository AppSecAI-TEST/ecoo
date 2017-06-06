package ecoo.ws.rest;

import ecoo.data.PackingList;
import ecoo.data.audit.Revision;
import ecoo.service.PackingListService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/shipments/pl")
public class PackingListResource extends BaseResource {

    private PackingListService packingListService;

    @Autowired
    public PackingListResource(PackingListService packingListService) {
        this.packingListService = packingListService;
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<PackingList> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(packingListService.findById(id));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<PackingList> delete(@PathVariable Integer id) {
        final PackingList packingList = packingListService.findById(id);
        return ResponseEntity.ok(packingListService.delete(packingList));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<PackingList> delete(@RequestBody PackingList packingList) {
        return ResponseEntity.ok(packingListService.delete(packingList));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final PackingList entity = packingListService.findById(id);
        return ResponseEntity.ok(packingListService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final PackingList entity = packingListService.findById(id);
        return ResponseEntity.ok(packingListService.findModifiedBy(entity));
    }
}