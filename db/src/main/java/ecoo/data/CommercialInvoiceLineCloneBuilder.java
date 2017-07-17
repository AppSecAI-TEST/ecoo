package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class CommercialInvoiceLineCloneBuilder {

    private CommercialInvoiceLine commercialInvoiceLine;

    private CommercialInvoiceLineCloneBuilder() {
    }

    public static CommercialInvoiceLineCloneBuilder aCommercialInvoiceLine() {
        return new CommercialInvoiceLineCloneBuilder();
    }

    public CommercialInvoiceLineCloneBuilder withCommercialInvoiceLine(CommercialInvoiceLine commercialInvoiceLine) {
        this.commercialInvoiceLine = commercialInvoiceLine;
        return this;
    }


    public CommercialInvoiceLine clone(Integer shipmentId) {
        CommercialInvoiceLine commercialInvoiceLine = new CommercialInvoiceLine();
        commercialInvoiceLine.setPrimaryId(null);
        commercialInvoiceLine.setParentId(shipmentId);
        commercialInvoiceLine.setMarks(this.commercialInvoiceLine.getMarks());
        commercialInvoiceLine.setProductCode(this.commercialInvoiceLine.getProductCode());
        commercialInvoiceLine.setDescr(this.commercialInvoiceLine.getDescr());
        commercialInvoiceLine.setQty(this.commercialInvoiceLine.getQty());
        commercialInvoiceLine.setPrice(this.commercialInvoiceLine.getPrice());
        commercialInvoiceLine.setAmount(this.commercialInvoiceLine.getAmount());
        return commercialInvoiceLine;
    }
}
