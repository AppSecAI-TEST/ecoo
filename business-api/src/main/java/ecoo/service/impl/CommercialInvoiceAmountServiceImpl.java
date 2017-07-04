package ecoo.service.impl;


import ecoo.dao.CommercialInvoiceAmountDao;
import ecoo.data.CommercialInvoiceAmount;
import ecoo.service.CommercialInvoiceAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service(value = "commercialInvoiceAmountService")
public final class CommercialInvoiceAmountServiceImpl extends AuditTemplate<Integer, CommercialInvoiceAmount, CommercialInvoiceAmountDao>
        implements CommercialInvoiceAmountService {

    private CommercialInvoiceAmountDao commercialInvoiceAmountDao;

    @Autowired
    public CommercialInvoiceAmountServiceImpl(CommercialInvoiceAmountDao commercialInvoiceAmountDao) {
        super(commercialInvoiceAmountDao);
        this.commercialInvoiceAmountDao = commercialInvoiceAmountDao;
    }

    /**
     * Returns the list of amounts for the given shipment.
     *
     * @param shipmentId The pk of the shipment.
     * @return A list.
     */
    @Override
    public List<CommercialInvoiceAmount> findByShipment(Integer shipmentId) {
        return commercialInvoiceAmountDao.findByShipment(shipmentId);
    }
}
