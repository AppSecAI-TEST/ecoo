package ecoo.service.impl;

import ecoo.audit.ShipmentAllChangesBuilder;
import ecoo.audit.ShipmentSingleChangeBuilder;
import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.dao.impl.es.ShipmentActivityGroupElasticsearchRepository;
import ecoo.data.*;
import ecoo.service.ShipmentActivityGroupService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public class ShipmentActivityGroupServiceImpl extends ElasticsearchTemplateService<Integer, ShipmentActivityGroup>
        implements ShipmentActivityGroupService {

    private ShipmentActivityGroupElasticsearchRepository shipmentActivityGroupElasticsearchRepository;

    private ShipmentAllChangesBuilder shipmentAllChangesBuilder;

    private ShipmentSingleChangeBuilder shipmentSingleChangeBuilder;

    @Autowired
    public ShipmentActivityGroupServiceImpl(ShipmentActivityGroupDao shipmentActivityGroupDao
            , @Qualifier("shipmentActivityGroupElasticsearchRepository") ShipmentActivityGroupElasticsearchRepository shipmentActivityGroupElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate
            , ShipmentAllChangesBuilder shipmentAllChangesBuilder
            , ShipmentSingleChangeBuilder shipmentSingleChangeBuilder) {
        super(shipmentActivityGroupDao, shipmentActivityGroupElasticsearchRepository, elasticsearchTemplate);
        this.shipmentActivityGroupElasticsearchRepository = shipmentActivityGroupElasticsearchRepository;
        this.shipmentAllChangesBuilder = shipmentAllChangesBuilder;
        this.shipmentSingleChangeBuilder = shipmentSingleChangeBuilder;
    }

    @Transactional
    @Override
    public ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Shipment shipment) {
        final ShipmentActivityGroup shipmentActivityGroup = shipmentSingleChangeBuilder.execute(modifiedBy, dateModified, shipment);
        if (shipmentActivityGroup != null) {
            return save(shipmentActivityGroup);
        }
        return null;
    }

    @Transactional
    @Override
    public ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Shipment shipment, String... description) {
        return recordActivity(modifiedBy, dateModified, shipment.getPrimaryId(), description);
    }

    @Transactional
    @Override
    public ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Integer shipmentId, String... description) {
        final ShipmentActivityGroupBuilder shipmentActivityGroupBuilder = ShipmentActivityGroupBuilder.aShipmentActivityGroup(modifiedBy
                , dateModified, shipmentId);
        for (String text : description) {
            shipmentActivityGroupBuilder.withLine(ShipmentActivityBuilder.aShipmentActivity()
                    .withDescr(text)
                    .build());
        }
        return save(shipmentActivityGroupBuilder.build());
    }

    @Transactional
    @Override
    public List<ShipmentActivityGroup> buildHistoryWithStartOfShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");

        final List<ShipmentActivityGroup> oldActivityGroups = findShipmentActivityGroupsByShipmentId(shipmentId);
        for (ShipmentActivityGroup oldActivityGroup : oldActivityGroups) {
            delete(oldActivityGroup);
        }

        final List<ShipmentActivityGroup> activityGroups = shipmentAllChangesBuilder.execute(shipmentId);
        saveAll(activityGroups);

        return activityGroups;
    }

    @Override
    public List<ShipmentActivityGroup> findShipmentActivityGroupsByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return shipmentActivityGroupElasticsearchRepository.findShipmentActivityGroupsByShipmentId(shipmentId);
    }
}
