package ecoo.service;

import ecoo.data.CommercialInvoice;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CommercialInvoiceService extends CrudService<Integer, CommercialInvoice>
        , AuditedModelAware<Integer, CommercialInvoice> {

}
