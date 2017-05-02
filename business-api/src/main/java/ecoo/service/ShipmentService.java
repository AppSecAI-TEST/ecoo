package ecoo.service;

import ecoo.data.Shipment;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface ShipmentService extends CrudService<Integer, Shipment>, AuditedModelAware<Shipment> {

    /**
     * Returns a list of shipments.
     *
     * @param q         The query value.
     * @param status    The status to evaluate.
     * @param start     The start row index.
     * @param pageSize  The page size.
     * @param column    The sort column index.
     * @param direction The direction to sort ASC or DESC.
     * @return A list.
     */
    List<Shipment> query(String q, String status, Integer start, Integer pageSize, Integer column, String direction);

    /**
     * Method to recreate the ES index.
     */
    void recreateIndex();
}
