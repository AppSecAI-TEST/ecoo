package ecoo.ws.cache.rest;

import ecoo.dao.impl.es.ShipmentElasticsearchIndexLoader;
import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.service.ShipmentService;
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
@RequestMapping("/api/shipments/cache")
public class ShipmentCacheResource extends BaseResource {

    private ShipmentElasticsearchRepository shipmentElasticsearchRepository;

    private ShipmentElasticsearchIndexLoader shipmentElasticsearchIndexLoader;

    private ShipmentService shipmentService;

    @Autowired
    public ShipmentCacheResource(@Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository
            , ShipmentElasticsearchIndexLoader shipmentElasticsearchIndexLoader
            , ShipmentService shipmentService) {
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;
        this.shipmentElasticsearchIndexLoader = shipmentElasticsearchIndexLoader;
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/recreate", method = RequestMethod.GET)
    public ResponseEntity<Boolean> recreate() {
        shipmentService.recreateIndex();
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "/forceRefresh", method = RequestMethod.GET)
    public ResponseEntity<Integer> forceRefresh() {
        shipmentElasticsearchRepository.deleteAll();
        return ResponseEntity.ok(shipmentElasticsearchIndexLoader.loadAll().size());
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(shipmentElasticsearchRepository.count());
    }
}