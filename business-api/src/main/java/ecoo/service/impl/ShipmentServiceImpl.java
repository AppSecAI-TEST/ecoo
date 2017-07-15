package ecoo.service.impl;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.dao.ShipmentDao;
import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Service
public class ShipmentServiceImpl extends ElasticsearchAuditTemplate<Integer, Shipment, ShipmentDao, ShipmentElasticsearchRepository>
        implements ShipmentService {

    private ShipmentElasticsearchRepository shipmentElasticsearchRepository;

    private String indexName;

    private String indexType;

    @Autowired
    public ShipmentServiceImpl(ShipmentDao shipmentDao
            , @Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(shipmentDao, shipmentElasticsearchRepository, elasticsearchTemplate);
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;

        this.indexName = Shipment.class.getAnnotation(Document.class).indexName();
        this.indexType = Shipment.class.getAnnotation(Document.class).type();
    }

    /**
     * Returns the shipment for the given process instance id.
     *
     * @param processInstanceId The BPM process instance id.
     * @return The shipment or null.
     */
    @Override
    public Shipment findByProcessInstanceId(String processInstanceId) {
        final List<Shipment> shipments = shipmentElasticsearchRepository.findShipmentsByProcessInstanceId(processInstanceId);
        if (shipments == null || shipments.isEmpty()) return null;
        return shipments.iterator().next();
    }

    /**
     * The method used to re-open a shipment.
     *
     * @param shipment The shipment to re-open.
     */
    @Transactional
    @Override
    public Shipment reopen(Shipment shipment) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");

        final ShipmentStatus shipmentStatus = ShipmentStatus.valueOfById(shipment.getStatus());
        switch (shipmentStatus) {
            case Cancelled:
                shipment.setStatus(ShipmentStatus.NewAndPendingSubmission.id());
                return save(shipment);
            default:
                throw new BusinessRuleViolationException(String.format("System cannot re-open shipment %s. " +
                        "Shipment is in status %s and cannot be re-open.", shipment.getPrimaryId(), shipmentStatus.name()));
        }
    }

    /**
     * Returns the count of the shipments associated to the given user.
     *
     * @param requestingUser The user asking to see the shipments.
     * @return The count.
     */
    @Override
    public Long countShipmentsAssociatedToUser(User requestingUser) {
        Assert.notNull(requestingUser, "The variable requestingUser cannot be null.");

        final BoolQueryBuilder queryBuilder = boolQuery();
        queryBuilder.must(matchQuery("ownerId", requestingUser.getPrimaryId()));

        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(indexName)
                .withTypes(indexType)
                .withQuery(queryBuilder)
                .withPageable(new PageRequest(0, Integer.MAX_VALUE))
                .build();
        return (long) queryForIds(searchQuery).size();
    }

    /**
     * Returns a list of shipments.
     *
     * @param q         The query value.
     * @param status    The status to evaluate.
     * @param start     The start row index.
     * @param pageSize  The page size.
     * @param column    The sort column index.
     * @param direction The direction to sort ASC or DESC.
     * @return A list.
     */
    @Override
    public List<Shipment> queryShipmentsAssociatedToUser(String q, String status, Integer start, Integer pageSize, Integer column
            , String direction, User requestingUser) {
        final PageRequest pageRequest = buildPageRequest(start, pageSize, column, direction);

        final BoolQueryBuilder queryBuilder = boolQuery();
        if (StringUtils.isNotBlank(q)) {
            final String escapedQuery = escape(q);
            queryBuilder.must(queryStringQuery("*" + escapedQuery + "*")
                    // TODO: Need to define other columns.
                    //.field("primaryId")
                    .field("exporterReference")
                    .analyzeWildcard(true));
        } else {
            queryBuilder.must(queryStringQuery("*")
                    // TODO: Need to define other columns.
                    .field("exporterReference")
                    .analyzeWildcard(true));
        }

        final BoolQueryBuilder ownerIdQueryBuilder = boolQuery();
        ownerIdQueryBuilder.must(matchQuery("ownerId", requestingUser.getPrimaryId()));
        queryBuilder.must(ownerIdQueryBuilder);

        final Iterable<Shipment> results = shipmentElasticsearchRepository.search(new NativeSearchQueryBuilder()
                .withIndices(indexName)
                .withTypes(indexType)
                .withQuery(queryBuilder)
                .withPageable(pageRequest)
                .build());
        if (results == null) {
            return new ArrayList<>();
        }

        if (status.equalsIgnoreCase("PENDING_ONLY")) {
            final List<Shipment> shipments = new ArrayList<>();
            for (Shipment shipment : results) {
                if (shipment.isInStatus(ShipmentStatus.NewAndPendingSubmission
                        , ShipmentStatus.SubmittedAndPendingChamberApproval
                        , ShipmentStatus.ApprovedAndPendingPayment)) {
                    shipments.add(shipment);
                }
            }
            return shipments;

        } else {
            return Lists.newArrayList(results.iterator());
        }
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

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    @Override
    protected void beforeSave(Shipment entity) {
        if (entity.isNew()) {
            entity.setDateSubmitted(new Date());
            entity.setStatus(ShipmentStatus.NewAndPendingSubmission.id());
        }
    }
}
