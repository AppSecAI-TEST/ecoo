package ecoo.ws.cache.rest;

import ecoo.dao.impl.es.UserElasticsearchIndexLoader;
import ecoo.dao.impl.es.UserElasticsearchRepository;
import ecoo.service.UserService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/users/cache")
public class UserCacheResource extends BaseResource {

    private UserElasticsearchRepository userElasticsearchRepository;

    private UserElasticsearchIndexLoader userElasticsearchIndexLoader;

    private UserService userService;

    @Autowired
    public UserCacheResource(@Qualifier("userElasticsearchRepository") UserElasticsearchRepository userElasticsearchRepository
            , UserElasticsearchIndexLoader userElasticsearchIndexLoader
            , UserService userService) {
        this.userElasticsearchRepository = userElasticsearchRepository;
        this.userElasticsearchIndexLoader = userElasticsearchIndexLoader;
        this.userService = userService;
    }

    @RequestMapping(value = "/recreate", method = RequestMethod.GET)
    public ResponseEntity<Boolean> recreate() {
        userService.recreateIndex();
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/forceRefresh", method = RequestMethod.GET)
    public ResponseEntity<Integer> forceRefresh() {
        userElasticsearchRepository.deleteAll();
        return ResponseEntity.ok(userElasticsearchIndexLoader.loadAll().size());
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(userElasticsearchRepository.count());
    }
}