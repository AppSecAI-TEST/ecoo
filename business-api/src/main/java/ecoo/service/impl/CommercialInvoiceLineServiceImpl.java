package ecoo.service.impl;


import ecoo.dao.CommercialInvoiceLineDao;
import ecoo.data.CommercialInvoiceLine;
import ecoo.service.CommercialInvoiceLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service(value = "commercialInvoiceLineService")
public final class CommercialInvoiceLineServiceImpl extends AuditTemplate<Integer, CommercialInvoiceLine, CommercialInvoiceLineDao>
        implements CommercialInvoiceLineService {

    private CommercialInvoiceLineDao commercialInvoiceLineDao;

    @Autowired
    public CommercialInvoiceLineServiceImpl(CommercialInvoiceLineDao commercialInvoiceLineDao) {
        super(commercialInvoiceLineDao);
        this.commercialInvoiceLineDao = commercialInvoiceLineDao;
    }

    /**
     * Returns the list of lines for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    @Override
    public List<CommercialInvoiceLine> findByShipment(Integer shipmentId) {
        return commercialInvoiceLineDao.findByShipment(shipmentId);
    }
}
