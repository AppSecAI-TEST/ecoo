package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class CommercialInvoiceCloneBuilder {

    private CommercialInvoice commercialInvoice;

    private CommercialInvoiceCloneBuilder() {
    }

    public static CommercialInvoiceCloneBuilder aCommercialInvoice() {
        return new CommercialInvoiceCloneBuilder();
    }

    public CommercialInvoiceCloneBuilder withCommercialInvoice(CommercialInvoice commercialInvoice) {
        this.commercialInvoice = commercialInvoice;
        return this;
    }

    public CommercialInvoice clone(Integer shipmentId) {
        CommercialInvoice commercialInvoice = new CommercialInvoice();
        commercialInvoice.setPrimaryId(shipmentId);
        commercialInvoice.setNotifyPartyName(this.commercialInvoice.getNotifyPartyName());
        commercialInvoice.setNotifyPartyBuilding(this.commercialInvoice.getNotifyPartyBuilding());
        commercialInvoice.setNotifyPartyStreet(this.commercialInvoice.getNotifyPartyStreet());
        commercialInvoice.setNotifyPartyCity(this.commercialInvoice.getNotifyPartyCity());
        commercialInvoice.setNotifyPartyPostcode(this.commercialInvoice.getNotifyPartyPostcode());
        commercialInvoice.setNotifyPartyProvince(this.commercialInvoice.getNotifyPartyProvince());
        commercialInvoice.setNotifyPartyCountry(this.commercialInvoice.getNotifyPartyCountry());
        commercialInvoice.setNotifyPartyPhoneNo(this.commercialInvoice.getNotifyPartyPhoneNo());
        commercialInvoice.setNotifyPartyEmail(this.commercialInvoice.getNotifyPartyEmail());
        commercialInvoice.setPaymentInstruction(this.commercialInvoice.getPaymentInstruction());
        return commercialInvoice;
    }
}
