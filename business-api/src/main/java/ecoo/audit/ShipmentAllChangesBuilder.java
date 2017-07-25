package ecoo.audit;

import ecoo.dao.ShipmentDao;
import ecoo.data.*;
import ecoo.data.audit.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class ShipmentAllChangesBuilder {

    private ShipmentDao shipmentDao;

    @Autowired
    public ShipmentAllChangesBuilder(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    public List<ShipmentActivityGroup> execute(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");

        final Map<Revision, Shipment> revisions = shipmentDao.findHistory(shipmentId);
        Assert.notNull(revisions, String.format("System cannot complete request to build shipment history. The system " +
                "found no revisions for shipment <%s>.", shipmentId));
        Assert.isTrue(!revisions.isEmpty(), String.format("System cannot complete request to build shipment history. The system " +
                "found no revisions for shipment <%s>.", shipmentId));

        final List<ShipmentActivityGroup> activityGroups = new ArrayList<>();
        if (revisions.keySet().size() == 1) {
            final Revision firstRevision = revisions.keySet().iterator().next();
            activityGroups.add(ShipmentActivityGroupBuilder.aShipmentActivityGroup(firstRevision.getModifiedBy()
                    , firstRevision.getDateModified(), shipmentId)
                    .withLine(ShipmentActivityBuilder.aShipmentActivity()
                            .withDescr("Shipment created.")
                            .build())
                    .build());
        } else {
            final Iterator<Revision> iterator = revisions.keySet().iterator();
            final Revision firstRevision = iterator.next();
            buildDifferences(revisions, iterator, firstRevision, activityGroups);
        }
        return activityGroups;
    }

    private void buildDifferences(final Map<Revision, Shipment> revisions, final Iterator<Revision> iterator
            , final Revision currentRevision, final List<ShipmentActivityGroup> activityGroup) {

        if (activityGroup.size() == 0) {
            final Shipment currentShipment = revisions.get(currentRevision);
            activityGroup.add(ShipmentActivityGroupBuilder
                    .aShipmentActivityGroup(currentRevision.getModifiedBy(), currentRevision.getDateModified()
                            , currentShipment.getPrimaryId())
                    .withLine(ShipmentActivityBuilder.aShipmentActivity()
                            .withDescr("Shipment created.")
                            .build())
                    .build());
            buildDifferences(revisions, iterator, currentRevision, activityGroup);

        } else {
            if (iterator.hasNext()) {
                final Revision otherRevision = iterator.next();

                final Shipment currentShipment = revisions.get(currentRevision);
                final Shipment otherShipment = revisions.get(otherRevision);

                final ShipmentDifferenceDetector shipmentDifferenceDetector = new ShipmentDifferenceDetector();
                final List<ShipmentActivity> activities = shipmentDifferenceDetector.execute(currentShipment, otherShipment);

                activityGroup.add(ShipmentActivityGroupBuilder
                        .aShipmentActivityGroup(otherRevision.getModifiedBy(), otherRevision.getDateModified()
                                , otherShipment.getPrimaryId())
                        .withLines(activities)
                        .build());

                buildDifferences(revisions, iterator, otherRevision, activityGroup);
            }
        }
    }
}
