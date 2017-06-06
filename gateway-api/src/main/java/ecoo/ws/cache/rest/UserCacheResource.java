package ecoo.ws.cache.rest;

import ecoo.command.RecreateElasticsearchUserIndexCommand;
import ecoo.command.ReloadElasticsearchUserIndexCommand;
import ecoo.dao.impl.es.UserElasticsearchRepository;
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

    private RecreateElasticsearchUserIndexCommand recreateElasticsearchUserIndexCommand;

    private ReloadElasticsearchUserIndexCommand reloadElasticsearchUserIndexCommand;

    @Autowired
    public UserCacheResource(@Qualifier("userElasticsearchRepository") UserElasticsearchRepository userElasticsearchRepository, RecreateElasticsearchUserIndexCommand recreateElasticsearchUserIndexCommand, ReloadElasticsearchUserIndexCommand reloadElasticsearchUserIndexCommand) {
        this.userElasticsearchRepository = userElasticsearchRepository;
        this.recreateElasticsearchUserIndexCommand = recreateElasticsearchUserIndexCommand;
        this.reloadElasticsearchUserIndexCommand = reloadElasticsearchUserIndexCommand;
    }

    @RequestMapping(value = "/recreate", method = RequestMethod.GET)
    public ResponseEntity<Boolean> recreate() {
        recreateElasticsearchUserIndexCommand.execute();
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/forceRefresh", method = RequestMethod.GET)
    public ResponseEntity<Integer> forceRefresh() {
        recreateElasticsearchUserIndexCommand.execute();
        return ResponseEntity.ok(reloadElasticsearchUserIndexCommand.execute());
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(userElasticsearchRepository.count());
    }
}