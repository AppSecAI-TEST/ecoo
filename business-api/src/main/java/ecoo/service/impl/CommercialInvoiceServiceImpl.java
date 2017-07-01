package ecoo.service.impl;

import ecoo.dao.CommercialInvoiceDao;
import ecoo.dao.CommercialInvoiceLineDao;
import ecoo.dao.impl.es.CommercialInvoiceElasticsearchRepository;
import ecoo.data.CommercialInvoice;
import ecoo.data.CommercialInvoiceLine;
import ecoo.service.CommercialInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class CommercialInvoiceServiceImpl extends ElasticsearchAuditTemplate<Integer
        , CommercialInvoice
        , CommercialInvoiceDao
        , CommercialInvoiceElasticsearchRepository> implements CommercialInvoiceService {

    private CommercialInvoiceLineDao commercialInvoiceLineDao;

    @Autowired
    public CommercialInvoiceServiceImpl(CommercialInvoiceDao dao
            , @Qualifier("commercialInvoiceElasticsearchRepository") CommercialInvoiceElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate
            , CommercialInvoiceLineDao commercialInvoiceLineDao) {
        super(dao, repository, elasticsearchTemplate);
        this.commercialInvoiceLineDao = commercialInvoiceLineDao;
    }

    @Transactional
    @Override
    public CommercialInvoice delete(CommercialInvoiceLine line) {
        Assert.notNull(line, "The variable line cannot be null.");

        final CommercialInvoice commercialInvoice = findById(line.getParentId());
        commercialInvoice.getLines().remove(line);

        return save(commercialInvoice);
    }

    @Override
    public CommercialInvoiceLine findLineById(Integer id) {
        return commercialInvoiceLineDao.findByPrimaryId(id);
    }

    @Transactional
    @Override
    public CommercialInvoiceLine save(CommercialInvoiceLine entity) {
        Assert.notNull(entity, "The variable entity cannot be null.");
        commercialInvoiceLineDao.save(entity);

        final CommercialInvoice commercialInvoice = findById(entity.getParentId());
        reload(commercialInvoice);

        return entity;
    }
}
