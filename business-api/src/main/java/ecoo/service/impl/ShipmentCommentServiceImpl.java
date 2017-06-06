package ecoo.service.impl;

import ecoo.dao.ShipmentCommentDao;
import ecoo.dao.impl.es.ShipmentCommentElasticsearchRepository;
import ecoo.data.ShipmentComment;
import ecoo.service.ShipmentCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Service
public class ShipmentCommentServiceImpl extends ElasticsearchAuditTemplate<Integer, ShipmentComment, ShipmentCommentDao, ShipmentCommentElasticsearchRepository>
        implements ShipmentCommentService {

    private ShipmentCommentElasticsearchRepository shipmentCommentElasticsearchRepository;

    @Autowired
    public ShipmentCommentServiceImpl(ShipmentCommentDao shipmentCommentDao
            , @Qualifier("shipmentCommentElasticsearchRepository") ShipmentCommentElasticsearchRepository shipmentCommentElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(shipmentCommentDao, shipmentCommentElasticsearchRepository, elasticsearchTemplate);
        this.shipmentCommentElasticsearchRepository = shipmentCommentElasticsearchRepository;
    }

    /**
     * Returns all the shipment comments for the give shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list of shipment related comments.
     */
    @Override
    public List<ShipmentComment> findByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The shipmentId variable cannot be null.");
        return shipmentCommentElasticsearchRepository.findShipmentCommentsByShipmentId(shipmentId);
    }

    /**
     * Method called before save is called.
     *
     * @param aComment The entity to save.
     */
    @Override
    protected void beforeSave(ShipmentComment aComment) {
        Assert.notNull(aComment, "System cannot complete request. Shipment cannot be null.");
        Assert.notNull(aComment.getShipmentId(), "System cannot complete request. Shipment ID cannot be null.");
        Assert.notNull(aComment.getUser(), "System cannot complete request. User cannot be null.");
        Assert.hasText(aComment.getText(), "System cannot complete request. Text cannot be null or blank.");

        if (aComment.isNew() && aComment.getDateCreated() == null) {
            aComment.setDateCreated(new Date());
        }
    }


}
