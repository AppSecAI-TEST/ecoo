package ecoo.command;

import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.data.Role;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class QueryShipmentCommand {

    private ShipmentElasticsearchRepository shipmentElasticsearchRepository;

    @Autowired
    public QueryShipmentCommand(@Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository) {
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;
    }

    public List<Shipment> execute(String q, String status, Integer start, Integer pageSize, Integer column
            , String direction, User requestingUser) {
        final PageRequest pageRequest = buildPageRequest(start, pageSize, column, direction);
        final String[] statusIds = buildStatusIds(status);

        if (requestingUser.isInRole(Role.ROLE_SYSADMIN)) {
            if (StringUtils.isNotBlank(q)) {
                if (NumberUtils.isNumber(q)) {
                    final Page<Shipment> resultSet = shipmentElasticsearchRepository
                            .findShipmentByPrimaryIdEqualsOrExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndStatusIn(Integer.parseInt(q)
                                    , q, q, q, statusIds, pageRequest);
                    return convertToShipmentList(resultSet);
                } else {
                    final Page<Shipment> resultSet = shipmentElasticsearchRepository
                            .findShipmentByExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndStatusIn(
                                    q, q, q, statusIds, pageRequest);
                    return convertToShipmentList(resultSet);
                }
            } else {
                final Page<Shipment> resultSet = shipmentElasticsearchRepository.findShipmentByStatusIn(statusIds, pageRequest);
                return convertToShipmentList(resultSet);
            }

        } else {
            if (StringUtils.isNotBlank(q)) {
                if (NumberUtils.isNumber(q)) {
                    final Page<Shipment> resultSet = shipmentElasticsearchRepository
                            .findShipmentByPrimaryIdEqualsOrExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndOwnerIdEqualsAndStatusIn(Integer.parseInt(q)
                                    , q, q, q, requestingUser.getPrimaryId(), statusIds, pageRequest);
                    return convertToShipmentList(resultSet);
                } else {
                    final Page<Shipment> resultSet = shipmentElasticsearchRepository
                            .findShipmentByExporterReferenceContainsOrPortOfLoadingContainsOrPortOfAcceptanceContainsAndOwnerIdEqualsAndStatusIn(
                                    q, q, q, requestingUser.getPrimaryId(), statusIds, pageRequest);
                    return convertToShipmentList(resultSet);
                }
            } else {
                final Page<Shipment> resultSet = shipmentElasticsearchRepository.findShipmentByOwnerIdEqualsAndStatusIn(requestingUser.getPrimaryId()
                        , statusIds, pageRequest);
                return convertToShipmentList(resultSet);
            }
        }
    }


    private String[] buildStatusIds(String status) {
        final Collection<String> statusIds = new ArrayList<>();
        if (status.equalsIgnoreCase("PENDING_ONLY")) {
            statusIds.add(ShipmentStatus.NewAndPendingSubmission.id());
            statusIds.add(ShipmentStatus.SubmittedAndPendingChamberApproval.id());

        } else if (status.equalsIgnoreCase("APPROVED_ONLY")) {
            statusIds.add(ShipmentStatus.Approved.id());

        } else if (status.equalsIgnoreCase("CANCELLED_ONLY")) {
            statusIds.add(ShipmentStatus.Cancelled.id());

        } else {
            for (ShipmentStatus e : ShipmentStatus.values()) {
                statusIds.add(e.id());
            }
        }
        return statusIds.toArray(new String[statusIds.size()]);
    }

    private List<Shipment> convertToShipmentList(Page<Shipment> resultSet) {
        final List<Shipment> shipments = new ArrayList<>();
        for (Shipment shipment : resultSet) {
            shipments.add(shipment);
        }
        return shipments;
    }

    private PageRequest buildPageRequest(int start, int pageSize, int column, String direction) {
        PageRequest pageRequest;

        String property;
        switch (column) {
            case 0:
                property = "primaryId";
                break;
            // TODO: Need to define other columns.
            default:
                property = "primaryId";
                break;
        }

        if (direction != null && direction.equalsIgnoreCase("ASC")) {
            pageRequest = new PageRequest(start, pageSize, Sort.Direction.ASC, property);
        } else {
            pageRequest = new PageRequest(start, pageSize, Sort.Direction.DESC, property);
        }
        return pageRequest;
    }
}
