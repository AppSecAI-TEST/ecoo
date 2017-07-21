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

    long countShipmentByOwnerIdEqualsAndStatusIn(Integer ownerId, String[] status);

    long countShipmentByOwnerIdEqualsAndDateSubmittedGreaterThanEqualAndDateSubmittedLessThanAndStatusIn(Integer ownerId
            , long dateSubmittedStart, long dateSubmittedEnd, String[] status);

    Page<Shipment> findShipmentByOwnerIdEqualsAndStatusIn(Integer ownerId, String[] status, Pageable pageable);

    Page<Shipment> findShipmentByPrimaryIdEqualsOrExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndOwnerIdEqualsAndStatusIn(Integer primaryId
            , String portOfLoading
            , String portOfAcceptance
            , String exporterReference
            , Integer ownerId
            , String[] status
            , Pageable pageable);

    Page<Shipment> findShipmentByExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndOwnerIdEqualsAndStatusIn(String exporterReference
            , String portOfLoading
            , String portOfAcceptance
            , Integer ownerId
            , String[] status
            , Pageable pageable);

    // No owner.
    Page<Shipment> findShipmentByStatusIn(String[] status, Pageable pageable);

    // No owner.
    Page<Shipment> findShipmentByPrimaryIdEqualsOrExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndStatusIn(Integer primaryId
            , String portOfLoading
            , String portOfAcceptance
            , String exporterReference
            , String[] status
            , Pageable pageable);

    // No owner.
    Page<Shipment> findShipmentByExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndStatusIn(String exporterReference
            , String portOfLoading
            , String portOfAcceptance
            , String[] status
            , Pageable pageable);

    List<Shipment> findShipmentByExporterReferenceEquals(String exporterReference);

    List<Shipment> findShipmentsByProcessInstanceId(String processInstanceId);

    List<Shipment> findShipmentsByStatus(String status);
}
