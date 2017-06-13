package ecoo.service.impl;

import ecoo.dao.ShipmentDocumentDao;
import ecoo.data.ShipmentDocument;
import ecoo.service.ShipmentDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class ShipmentDocumentServiceImpl extends AuditTemplate<Integer, ShipmentDocument, ShipmentDocumentDao>
        implements ShipmentDocumentService {

    private ShipmentDocumentDao shipmentDocumentDao;

    @Autowired
    public ShipmentDocumentServiceImpl(ShipmentDocumentDao shipmentDocumentDao) {
        super(shipmentDocumentDao);
        this.shipmentDocumentDao = shipmentDocumentDao;
    }

    /**
     * Returns the shipment document for the shipment and the type.
     *
     * @param shipmentId   The shipment pk.
     * @param documentType The document type.
     * @return The company document or null.
     */
    @Override
    public ShipmentDocument findByShipmentAndType(Integer shipmentId, String documentType) {
        return shipmentDocumentDao.findByShipmentAndType(shipmentId, documentType);
    }

    /**
     * Returns the list of documents for the given shipment.
     *
     * @param shipmentId The shipment pk.
     * @return A list.
     */
    @Override
    public List<ShipmentDocument> findByShipment(Integer shipmentId) {
        return shipmentDocumentDao.findByShipment(shipmentId);
    }
}
