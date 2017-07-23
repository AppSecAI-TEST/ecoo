package ecoo.data;

import ecoo.data.audit.Revision;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentActivityGroupBuilder {

    private Integer shipmentId;
    private Revision revision;
    private List<ShipmentActivity> activities = new ArrayList<>();

    private ShipmentActivityGroupBuilder(Integer shipmentId, Revision revision) {
        this.shipmentId = shipmentId;
        this.revision = revision;
    }

    public static ShipmentActivityGroupBuilder aShipmentActivityGroup(Integer shipmentId, Revision revision) {
        return new ShipmentActivityGroupBuilder(shipmentId, revision);
    }

    public ShipmentActivityGroupBuilder withLine(ShipmentActivity activity) {
        this.activities.add(activity);
        return this;
    }

    public ShipmentActivityGroupBuilder withLines(List<ShipmentActivity> activities) {
        this.activities.addAll(activities);
        return this;
    }

    public ShipmentActivityGroup build() {
        final User modifiedBy = revision.getModifiedBy();
        final ShipmentActivityGroup shipmentActivityGroup = new ShipmentActivityGroup(modifiedBy.getPrimaryId(),
                modifiedBy.getDisplayName(), shipmentId, revision.getDateModified());
        shipmentActivityGroup.setActivities(activities);
        return shipmentActivityGroup;
    }
}
