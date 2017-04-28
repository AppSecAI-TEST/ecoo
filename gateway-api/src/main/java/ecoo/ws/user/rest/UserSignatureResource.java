package ecoo.ws.user.rest;

import ecoo.data.audit.Revision;
import ecoo.data.UserSignature;
import ecoo.service.UserSignatureService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/users/signatures")
public class UserSignatureResource extends BaseResource {

    private UserSignatureService userSignatureService;

    @Autowired
    public UserSignatureResource(UserSignatureService userSignatureService) {
        this.userSignatureService = userSignatureService;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<UserSignature>> findByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userSignatureService.findByUser(userId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserSignature> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userSignatureService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<UserSignature> save(@RequestBody UserSignature entity) {
        return ResponseEntity.ok(userSignatureService.save(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<UserSignature> delete(@RequestBody UserSignature entity) {
        return ResponseEntity.ok(userSignatureService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final UserSignature entity = userSignatureService.findById(id);
        return ResponseEntity.ok(userSignatureService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final UserSignature entity = userSignatureService.findById(id);
        return ResponseEntity.ok(userSignatureService.findModifiedBy(entity));
    }
}