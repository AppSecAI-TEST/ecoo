package ecoo.data;

import ecoo.data.audit.Revision;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class ShipmentActivityGroupBuilder {

    private User modifiedBy;
    private Date dateModified;
    private Integer shipmentId;
    private List<ShipmentActivity> activities = new ArrayList<>();

    private ShipmentActivityGroupBuilder(User modifiedBy, Date dateModified, Integer shipmentId) {
        this.modifiedBy = modifiedBy;
        this.dateModified = dateModified;
        this.shipmentId = shipmentId;
    }

    public static ShipmentActivityGroupBuilder aShipmentActivityGroup(Revision revision, Integer shipmentId) {
        return new ShipmentActivityGroupBuilder(revision.getModifiedBy(), revision.getDateModified(), shipmentId);
    }


    public static ShipmentActivityGroupBuilder aShipmentActivityGroup(User modifiedBy, DateTime dateModified, Integer shipmentId) {
        return new ShipmentActivityGroupBuilder(modifiedBy, dateModified.toDate(), shipmentId);
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
        if (activities == null || activities.isEmpty()) return null;
        final ShipmentActivityGroup shipmentActivityGroup = new ShipmentActivityGroup(modifiedBy.getPrimaryId(),
                modifiedBy.getDisplayName(), shipmentId, dateModified);
        shipmentActivityGroup.setActivities(activities);
        return shipmentActivityGroup;
    }
}
