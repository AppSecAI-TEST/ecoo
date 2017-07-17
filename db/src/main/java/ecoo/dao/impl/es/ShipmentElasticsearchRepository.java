package ecoo.dao.impl.es;

import ecoo.data.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Repository(value = "shipmentElasticsearchRepository")
public interface ShipmentElasticsearchRepository extends ElasticsearchRepository<Shipment, Integer> {

    Page<Shipment> findShipmentByOwnerIdEqualsAndStatusIn(Integer ownerId, String[] status, Pageable pageable);

    Page<Shipment> findShipmentByExporterReferenceContainsAndOwnerIdEqualsAndStatusIn(String exporterReference
            , Integer ownerId
            , String[] status
            , Pageable pageable);

    Page<Shipment> findShipmentByPrimaryIdEqualsOrExporterReferenceContainsAndOwnerIdEqualsAndStatusIn(Integer primaryId
            , String exporterReference
            , Integer ownerId
            , String[] status
            , Pageable pageable);

    List<Shipment> findShipmentByExporterReferenceEquals(String exporterReference);

    List<Shipment> findShipmentsByProcessInstanceId(String processInstanceId);

    List<Shipment> findShipmentsByStatus(String status);
}
