package ecoo.ws.cache.rest;

import ecoo.dao.impl.es.ShipmentCommentElasticsearchIndexLoader;
import ecoo.dao.impl.es.ShipmentCommentElasticsearchRepository;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/shipments/comments/cache")
public class ShipmentCommentCacheResource extends BaseResource {

    private ShipmentCommentElasticsearchRepository shipmentCommentElasticsearchRepository;

    private ShipmentCommentElasticsearchIndexLoader shipmentCommentElasticsearchIndexLoader;

    @Autowired
    public ShipmentCommentCacheResource(@Qualifier("shipmentCommentElasticsearchRepository") ShipmentCommentElasticsearchRepository shipmentCommentElasticsearchRepository
            , ShipmentCommentElasticsearchIndexLoader shipmentCommentElasticsearchIndexLoader) {
        this.shipmentCommentElasticsearchRepository = shipmentCommentElasticsearchRepository;
        this.shipmentCommentElasticsearchIndexLoader = shipmentCommentElasticsearchIndexLoader;
    }

    @RequestMapping(value = "/recreate", method = RequestMethod.GET)
    public ResponseEntity<Boolean> recreate() {
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/forceRefresh", method = RequestMethod.GET)
    public ResponseEntity<Integer> forceRefresh() {
        shipmentCommentElasticsearchRepository.deleteAll();
        return ResponseEntity.ok(shipmentCommentElasticsearchIndexLoader.loadAll().size());
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(shipmentCommentElasticsearchRepository.count());
    }
}