package ecoo.service.impl;

import ecoo.audit.ShipmentAllChangesBuilder;
import ecoo.audit.ShipmentSingleChangeBuilder;
import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.dao.impl.es.ShipmentActivityGroupElasticsearchRepository;
import ecoo.data.Shipment;
import ecoo.data.ShipmentActivityBuilder;
import ecoo.data.ShipmentActivityGroup;
import ecoo.data.User;
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

    private ShipmentActivityGroupDao shipmentActivityGroupDao;

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
        this.shipmentActivityGroupDao = shipmentActivityGroupDao;
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
    public ShipmentActivityGroup recordActivity(User modifiedBy, DateTime dateModified, Shipment shipment, String description) {
        final ShipmentActivityGroup shipmentActivityGroup = new ShipmentActivityGroup(modifiedBy.getPrimaryId(),
                modifiedBy.getDisplayName(), shipment.getPrimaryId(), dateModified.toDate());

        shipmentActivityGroup.addActivity(ShipmentActivityBuilder.aShipmentActivity()
                .withDescr(description)
                .build());

        return save(shipmentActivityGroup);
    }

    @Transactional
    @Override
    public List<ShipmentActivityGroup> buildHistoryWithStartOfShipment(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");

        shipmentActivityGroupDao.deleteAllActivitiesByShipment(shipmentId);

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
