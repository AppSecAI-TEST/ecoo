package ecoo.service;

import ecoo.data.CommercialInvoiceAmount;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface CommercialInvoiceAmountService extends CrudService<Integer, CommercialInvoiceAmount>
        , AuditedModelAware<Integer, CommercialInvoiceAmount> {

    /**
     * Returns the list of amounts for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    List<CommercialInvoiceAmount> findByShipment(Integer shipmentId);

}
