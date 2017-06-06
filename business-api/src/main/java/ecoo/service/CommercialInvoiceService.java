package ecoo.service;

import ecoo.data.CommercialInvoice;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface CommercialInvoiceService extends CrudService<Integer, CommercialInvoice>, AuditedModelAware<CommercialInvoice> {

    List<CommercialInvoice> findCommercialInvoicesByShipmentId(Integer shipmentId);
}
