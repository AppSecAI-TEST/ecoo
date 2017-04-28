package ecoo.ws.user.rest;

import ecoo.data.Role;
import ecoo.data.User;
import ecoo.security.GrantRoleConfirmation;
import ecoo.service.UserService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/userRoles")
public class UserRoleResource extends BaseResource {

    private UserService userService;

    @Autowired
    public UserRoleResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/grant/userId/{userId}", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GrantRoleConfirmation> grant(@PathVariable Integer userId, @RequestBody String roleName) {
        Assert.notNull(userId);
        Assert.notNull(roleName);

        final User user = userService.findById(userId);

        return ResponseEntity.ok(userService.grant(user, Role.valueOf(roleName)));
    }

    @RequestMapping(value = "/revoke/userId/{userId}", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> revoke(@PathVariable Integer userId, @RequestBody String roleName) {
        Assert.notNull(userId);
        Assert.notNull(roleName);

        final User user = userService.findById(userId);
        return ResponseEntity.ok(userService.revoke(user, roleName));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@RequestBody User user) {
        return ResponseEntity.ok(userService.delete(user));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }
}