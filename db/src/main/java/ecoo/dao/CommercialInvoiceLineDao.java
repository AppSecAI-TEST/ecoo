package ecoo.dao;

import ecoo.data.CommercialInvoiceLine;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface CommercialInvoiceLineDao extends AuditLogDao<Integer, CommercialInvoiceLine> {
    /**
     * Returns the list of lines for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    List<CommercialInvoiceLine> findByShipment(Integer shipmentId);
}
