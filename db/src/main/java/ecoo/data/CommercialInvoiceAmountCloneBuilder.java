package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class CommercialInvoiceAmountCloneBuilder {

    private CommercialInvoiceAmount commercialInvoiceAmount;

    private CommercialInvoiceAmountCloneBuilder() {
    }

    public static CommercialInvoiceAmountCloneBuilder aCommercialInvoiceAmount() {
        return new CommercialInvoiceAmountCloneBuilder();
    }

    public CommercialInvoiceAmountCloneBuilder withCommercialInvoiceAmount(CommercialInvoiceAmount commercialInvoiceAmount) {
        this.commercialInvoiceAmount = commercialInvoiceAmount;
        return this;
    }

    public CommercialInvoiceAmount clone(Integer shipmentId) {
        final CommercialInvoiceAmount commercialInvoiceAmount = new CommercialInvoiceAmount();
        commercialInvoiceAmount.setPrimaryId(null);
        commercialInvoiceAmount.setShipmentId(shipmentId);
        commercialInvoiceAmount.setAmountType(this.commercialInvoiceAmount.getAmountType());
        commercialInvoiceAmount.setAmount(this.commercialInvoiceAmount.getAmount());
        return commercialInvoiceAmount;
    }
}
