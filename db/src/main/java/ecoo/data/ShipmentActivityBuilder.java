package ecoo.data;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentActivityBuilder {

    private String descr;

    private ShipmentActivityBuilder() {
    }

    public static ShipmentActivityBuilder aShipmentActivity() {
        return new ShipmentActivityBuilder();
    }

    public ShipmentActivityBuilder withDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public ShipmentActivity build() {
        ShipmentActivity shipmentActivity = new ShipmentActivity();
        shipmentActivity.setDescr(descr);
        return shipmentActivity;
    }
}
