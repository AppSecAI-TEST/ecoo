package ecoo.service;

import ecoo.data.ShipmentDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface ShipmentDocumentService extends CrudService<Integer, ShipmentDocument>, AuditedModelAware<ShipmentDocument> {


    /**
     * Returns the shipment document for the shipment and the type.
     *
     * @param shipmentId   The shipment pk.
     * @param documentType The document type.
     * @return The company document or null.
     */
    ShipmentDocument findByShipmentAndType(Integer shipmentId, String documentType);

    /**
     * Returns the list of documents for the given shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list.
     */
    List<ShipmentDocument> findByShipment(Integer shipmentId);
}
