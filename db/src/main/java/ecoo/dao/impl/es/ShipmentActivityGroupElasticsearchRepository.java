package ecoo.dao.impl.es;

import ecoo.data.ShipmentActivityGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Repository(value = "shipmentActivityGroupElasticsearchRepository")
public interface ShipmentActivityGroupElasticsearchRepository extends ElasticsearchRepository<ShipmentActivityGroup, Integer> {

    List<ShipmentActivityGroup> findShipmentActivityGroupsByShipmentId(Integer shipmentId);
}
