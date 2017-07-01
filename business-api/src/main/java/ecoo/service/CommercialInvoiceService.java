package ecoo.service;

import ecoo.data.CommercialInvoice;
import ecoo.data.CommercialInvoiceLine;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CommercialInvoiceService extends CrudService<Integer, CommercialInvoice>, AuditedModelAware<CommercialInvoice> {

    CommercialInvoice delete(CommercialInvoiceLine line);

    CommercialInvoiceLine findLineById(Integer id);

    CommercialInvoiceLine save(CommercialInvoiceLine entity);
}
