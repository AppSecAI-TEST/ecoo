package ecoo.ws.user.rest;

import ecoo.data.ChamberUser;
import ecoo.data.audit.Revision;
import ecoo.service.ChamberUserService;
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
@RequestMapping("/api/chambers/users")
public class ChamberUserResource extends BaseResource {

    private ChamberUserService chamberUserService;

    @Autowired
    public ChamberUserResource(ChamberUserService chamberUserService) {
        this.chamberUserService = chamberUserService;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ChamberUser>> findByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(chamberUserService.findByUser(userId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ChamberUser> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chamberUserService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ChamberUser> save(@RequestBody ChamberUser entity) {
        return ResponseEntity.ok(chamberUserService.save(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<ChamberUser> delete(@RequestBody ChamberUser entity) {
        return ResponseEntity.ok(chamberUserService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final ChamberUser entity = chamberUserService.findById(id);
        return ResponseEntity.ok(chamberUserService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final ChamberUser entity = chamberUserService.findById(id);
        return ResponseEntity.ok(chamberUserService.findModifiedBy(entity));
    }
}