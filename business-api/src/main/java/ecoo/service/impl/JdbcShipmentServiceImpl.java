package ecoo.service.impl;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.dao.ShipmentDao;
import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
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
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Service
public class JdbcShipmentServiceImpl extends JdbcElasticsearchAuditTemplate<Integer, Shipment, ShipmentDao, ShipmentElasticsearchRepository>
        implements ShipmentService {

    private ShipmentElasticsearchRepository shipmentElasticsearchRepository;

    private String indexName;

    private String indexType;

    @Autowired
    public JdbcShipmentServiceImpl(ShipmentDao shipmentDao
            , @Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(shipmentDao, shipmentElasticsearchRepository, elasticsearchTemplate, Shipment.class);
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;

        this.indexName = Shipment.class.getAnnotation(Document.class).indexName();
        this.indexType = Shipment.class.getAnnotation(Document.class).type();
    }

    /**
     * The method used to cancel a shipment.
     *
     * @param shipment The shipment to cancel.
     */
    @Transactional
    @Override
    public Shipment cancel(Shipment shipment) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");

        final ShipmentStatus shipmentStatus = ShipmentStatus.valueOfById(shipment.getStatus());
        switch (shipmentStatus) {
            case NewAndPendingSubmission:
                shipment.setStatus(ShipmentStatus.Cancelled.id());
                return save(shipment);
            case SubmittedAndPendingChamberApproval:
                // TODO: Need to call workflow to cancel process instance.
                shipment.setStatus(ShipmentStatus.Cancelled.id());
                return save(shipment);
            default:
                throw new BusinessRuleViolationException(String.format("System cannot cancel shipment %s. " +
                        "Shipment is in status %s and cannot be cancelled.", shipment.getPrimaryId(), shipmentStatus.name()));
        }
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
    public List<Shipment> query(String q, String status, Integer start, Integer pageSize, Integer column, String
            direction) {
        final PageRequest pageRequest = buildPageRequest(start, pageSize, column, direction);

        final BoolQueryBuilder queryBuilder = boolQuery();
        if (StringUtils.isNotBlank(q)) {
            final String escapedQuery = escape(q);
            queryBuilder.must(queryStringQuery("*" + escapedQuery + "*")
                    // TODO: Need to define other columns.
                    .field("exporterReference")
                    .analyzeWildcard(true));
        } else {
            queryBuilder.must(queryStringQuery("*")
                    // TODO: Need to define other columns.
                    .field("exporterReference")
                    .analyzeWildcard(true));
        }

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
