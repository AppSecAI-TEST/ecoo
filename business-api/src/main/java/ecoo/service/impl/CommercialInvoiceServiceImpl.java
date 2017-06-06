package ecoo.service.impl;

import ecoo.dao.CommercialInvoiceDao;
import ecoo.dao.impl.es.CommercialInvoiceElasticsearchRepository;
import ecoo.data.CommercialInvoice;
import ecoo.service.CommercialInvoiceService;
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
public class CommercialInvoiceServiceImpl extends ElasticsearchAuditTemplate<Integer
        , CommercialInvoice
        , CommercialInvoiceDao
        , CommercialInvoiceElasticsearchRepository> implements CommercialInvoiceService {

    private CommercialInvoiceElasticsearchRepository repository;

    @Autowired
    public CommercialInvoiceServiceImpl(CommercialInvoiceDao dao
            , @Qualifier("commercialInvoiceElasticsearchRepository") CommercialInvoiceElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate);
        this.repository = repository;
    }

    @Override
    public List<CommercialInvoice> findCommercialInvoicesByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return repository.findCommercialInvoicesByShipmentId(shipmentId);
    }
}
