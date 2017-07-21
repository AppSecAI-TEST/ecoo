package ecoo.service.impl;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.command.QueryShipmentCommand;
import ecoo.dao.ShipmentDao;
import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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

    private QueryShipmentCommand queryShipmentCommand;

    @Autowired
    public ShipmentServiceImpl(ShipmentDao shipmentDao
            , @Qualifier("shipmentElasticsearchRepository") ShipmentElasticsearchRepository shipmentElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate
            , QueryShipmentCommand queryShipmentCommand) {
        super(shipmentDao, shipmentElasticsearchRepository, elasticsearchTemplate);
        this.shipmentElasticsearchRepository = shipmentElasticsearchRepository;
        this.queryShipmentCommand = queryShipmentCommand;

        this.indexName = Shipment.class.getAnnotation(Document.class).indexName();
        this.indexType = Shipment.class.getAnnotation(Document.class).type();
    }

    /**
     * Count the number of shipments.
     *
     * @param ownerId The user that requested the shipment
     * @param status  The status(es) to evaluate.
     * @return The count.
     */
    @Override
    public long count(Integer ownerId, ShipmentStatus... status) {
        final String[] statusArray = new String[status.length];
        for (int i = 0; i < status.length; i++) statusArray[i] = status[i].id();
        return shipmentElasticsearchRepository.countShipmentByOwnerIdEqualsAndStatusIn(ownerId, statusArray);
    }

    /**
     * Count the number of shipments.
     *
     * @param ownerId   The user that requested the shipment
     * @param startDate The start date.
     * @param endDate   The end date.
     * @param status    The status(es) to evaluate.
     * @return The count.
     */
    @Override
    public long count(Integer ownerId, DateTime startDate, DateTime endDate, ShipmentStatus... status) {
        final String[] statusArray = new String[status.length];
        for (int i = 0; i < status.length; i++) statusArray[i] = status[i].id();
        return shipmentElasticsearchRepository.countShipmentByOwnerIdEqualsAndDateSubmittedGreaterThanEqualAndDateSubmittedLessThanAndStatusIn(ownerId
                , startDate.getMillis(), endDate.getMillis(), statusArray);
    }

    /**
     * Returns the shipment for the given exporter reference.
     *
     * @param exporterReference The exporter reference.
     * @return The shipment.
     */
    @Override
    public Shipment findShipmentByExporterReference(String exporterReference) {
        final List<Shipment> shipments = shipmentElasticsearchRepository.findShipmentByExporterReferenceEquals(exporterReference);
        if (shipments == null || shipments.isEmpty()) return null;
        return shipments.iterator().next();
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
        return queryShipmentCommand.execute(q, status, start, pageSize, column, direction, requestingUser);
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
