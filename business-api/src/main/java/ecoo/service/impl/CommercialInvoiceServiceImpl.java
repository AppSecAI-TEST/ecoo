package ecoo.service.impl;

import ecoo.dao.CommercialInvoiceDao;
import ecoo.dao.impl.es.CommercialInvoiceElasticsearchRepository;
import ecoo.data.CommercialInvoice;
import ecoo.service.CommercialInvoiceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class CommercialInvoiceServiceImpl extends ElasticsearchAuditTemplate<Integer
        , CommercialInvoice
        , CommercialInvoiceDao
        , CommercialInvoiceElasticsearchRepository> implements CommercialInvoiceService {

    @Autowired
    public CommercialInvoiceServiceImpl(CommercialInvoiceDao dao
            , @Qualifier("commercialInvoiceElasticsearchRepository") CommercialInvoiceElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate);
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

        // DEVNOTE: In time implement the ability to show the differences changed on the commercial invoice.
//        final UserAuthentication authentication = (UserAuthentication) SecurityContextHolder
//                .getContext().getAuthentication();
//        final User currentUser = (User) authentication.getDetails();
//        Assert.notNull(currentUser, "System cannot complete request. No security principle set.");
//
//        shipmentActivityGroupService.recordActivity(currentUser, DateTime.now(), entity.getPrimaryId()
//                , "Commercial invoice amended.");
    }
}
