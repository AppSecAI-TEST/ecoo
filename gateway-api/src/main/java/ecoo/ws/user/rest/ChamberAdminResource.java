package ecoo.ws.user.rest;

import ecoo.data.ChamberAdmin;
import ecoo.data.audit.Revision;
import ecoo.service.ChamberAdminService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/chambers/admins")
public class ChamberAdminResource extends BaseResource {

    private ChamberAdminService chamberUserService;

    @Autowired
    public ChamberAdminResource(ChamberAdminService chamberUserService) {
        this.chamberUserService = chamberUserService;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<ChamberAdmin>> findByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(chamberUserService.findByUser(userId));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final ChamberAdmin entity = chamberUserService.findById(id);
        return ResponseEntity.ok(chamberUserService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final ChamberAdmin entity = chamberUserService.findById(id);
        return ResponseEntity.ok(chamberUserService.findModifiedBy(entity));
    }
}