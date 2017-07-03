package ecoo.service.impl;

import ecoo.dao.CommercialInvoiceDao;
import ecoo.dao.CommercialInvoiceLineDao;
import ecoo.dao.impl.es.CommercialInvoiceElasticsearchRepository;
import ecoo.data.CommercialInvoice;
import ecoo.data.CommercialInvoiceLine;
import ecoo.service.CommercialInvoiceService;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    @Override
    protected void beforeSave(CommercialInvoice entity) {
        entity.setNotifyPartyName(StringUtils.stripToNull(entity.getNotifyPartyName()));
        entity.setNotifyPartyBuilding(StringUtils.stripToNull(entity.getNotifyPartyBuilding()));
        entity.setNotifyPartyStreet(StringUtils.stripToNull(entity.getNotifyPartyStreet()));
        entity.setNotifyPartyCity(StringUtils.stripToNull(entity.getNotifyPartyCity()));
        entity.setNotifyPartyPostcode(StringUtils.stripToNull(entity.getNotifyPartyPostcode()));
        entity.setNotifyPartyProvince(StringUtils.stripToNull(entity.getNotifyPartyProvince()));
        entity.setNotifyPartyCountry(StringUtils.stripToNull(entity.getNotifyPartyCountry()));
        entity.setNotifyPartyPhoneNo(StringUtils.stripToNull(entity.getNotifyPartyPhoneNo()));
        entity.setNotifyPartyEmail(StringUtils.stripToNull(entity.getNotifyPartyEmail()));
        entity.setPaymentInstruction(StringUtils.stripToNull(entity.getPaymentInstruction()));
    }
}
