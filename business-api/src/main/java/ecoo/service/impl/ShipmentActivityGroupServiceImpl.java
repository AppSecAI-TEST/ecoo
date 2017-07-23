package ecoo.service.impl;

import ecoo.audit.ShipmentHistoryBuilder;
import ecoo.dao.ShipmentActivityGroupDao;
import ecoo.dao.impl.es.ShipmentActivityGroupElasticsearchRepository;
import ecoo.data.ShipmentActivityGroup;
import ecoo.service.ShipmentActivityGroupService;
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

    private ShipmentHistoryBuilder shipmentHistoryBuilder;

    @Autowired
    public ShipmentActivityGroupServiceImpl(ShipmentActivityGroupDao shipmentActivityGroupDao
            , @Qualifier("shipmentActivityGroupElasticsearchRepository") ShipmentActivityGroupElasticsearchRepository shipmentActivityGroupElasticsearchRepository
            , ElasticsearchTemplate elasticsearchTemplate
            , ShipmentHistoryBuilder shipmentHistoryBuilder) {
        super(shipmentActivityGroupDao, shipmentActivityGroupElasticsearchRepository, elasticsearchTemplate);
        this.shipmentActivityGroupElasticsearchRepository = shipmentActivityGroupElasticsearchRepository;
        this.shipmentHistoryBuilder = shipmentHistoryBuilder;
    }

    @Transactional
    @Override
    public List<ShipmentActivityGroup> buildAllHistory(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");

        final List<ShipmentActivityGroup> activityGroups = shipmentHistoryBuilder.execute(shipmentId);
        saveAll(activityGroups);

        return activityGroups;
    }

    @Override
    public List<ShipmentActivityGroup> findShipmentActivityGroupsByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return shipmentActivityGroupElasticsearchRepository.findShipmentActivityGroupsByShipmentId(shipmentId);
    }
}
