package ecoo.dao.impl.es;

import ecoo.data.Shipment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Repository(value = "shipmentElasticsearchRepository")
public interface ShipmentElasticsearchRepository extends ElasticsearchRepository<Shipment, Integer> {

    List<Shipment> findShipmentByExporterReferenceEquals(String exporterReference);

    List<Shipment> findShipmentsByProcessInstanceId(String processInstanceId);

    List<Shipment> findShipmentsByStatus(String status);
}
