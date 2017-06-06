package ecoo.ws.cache.rest;

import ecoo.command.RecreateElasticsearchShipmentIndexCommand;
import ecoo.command.ReloadElasticsearchShipmentIndexCommand;
import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
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

    private RecreateElasticsearchShipmentIndexCommand recreateElasticsearchShipmentIndexCommand;

    private ReloadElasticsearchShipmentIndexCommand reloadElasticsearchShipmentIndexCommand;

    @Autowired
    public ShipmentCacheResource(@Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository, RecreateElasticsearchShipmentIndexCommand recreateElasticsearchShipmentIndexCommand, ReloadElasticsearchShipmentIndexCommand reloadElasticsearchShipmentIndexCommand) {
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;
        this.recreateElasticsearchShipmentIndexCommand = recreateElasticsearchShipmentIndexCommand;
        this.reloadElasticsearchShipmentIndexCommand = reloadElasticsearchShipmentIndexCommand;
    }

    @RequestMapping(value = "/recreate", method = RequestMethod.GET)
    public ResponseEntity<Boolean> recreate() {
        return ResponseEntity.ok(recreateElasticsearchShipmentIndexCommand.execute());
    }

    @RequestMapping(value = "/forceRefresh", method = RequestMethod.GET)
    public ResponseEntity<Integer> forceRefresh() {
        recreateElasticsearchShipmentIndexCommand.execute();
        return ResponseEntity.ok(reloadElasticsearchShipmentIndexCommand.execute());
    }

    @RequestMapping(value = "/size", method = RequestMethod.GET)
    public ResponseEntity<Long> size() {
        return ResponseEntity.ok(shipmentElasticsearchRepository.count());
    }
}