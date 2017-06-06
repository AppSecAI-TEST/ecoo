package ecoo.service.impl;

import ecoo.dao.PackingListDao;
import ecoo.dao.impl.es.PackingListElasticsearchRepository;
import ecoo.data.PackingList;
import ecoo.service.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class PackListServiceImpl extends ElasticsearchAuditTemplate<Integer
        , PackingList
        , PackingListDao
        , PackingListElasticsearchRepository> implements PackingListService {

    private PackingListElasticsearchRepository repository;

    @Autowired
    public PackListServiceImpl(PackingListDao dao
            , @Qualifier("packingListElasticsearchRepository") PackingListElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate);
        this.repository = repository;
    }

    @Override
    public List<PackingList> findPackingListsByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return repository.findPackingListsByShipmentId(shipmentId);
    }
}
