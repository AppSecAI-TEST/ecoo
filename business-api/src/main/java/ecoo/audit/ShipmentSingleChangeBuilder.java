package ecoo.audit;

import ecoo.dao.ShipmentDao;
import ecoo.data.*;
import ecoo.data.audit.Revision;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class ShipmentSingleChangeBuilder {

    private ShipmentDao shipmentDao;

    @Autowired
    public ShipmentSingleChangeBuilder(ShipmentDao shipmentDao) {
        this.shipmentDao = shipmentDao;
    }

    public ShipmentActivityGroup execute(User modifiedBy, DateTime dateModified, Shipment shipment) {
        Assert.notNull(shipment, "The variable shipment cannot be null.");
        Assert.notNull(shipment.getPrimaryId(), "The variable shipment.primaryId cannot be null.");

        final Map<Revision, Shipment> revisions = shipmentDao.findMostRecentRevision(shipment);
        Assert.notNull(revisions, String.format("System cannot complete request to build shipment history. The system " +
                "found no revisions for shipment <%s>.", shipment.getPrimaryId()));

        if (revisions.isEmpty()) {
            return ShipmentActivityGroupBuilder.aShipmentActivityGroup(modifiedBy, dateModified, shipment.getPrimaryId())
                    .withLine(ShipmentActivityBuilder.aShipmentActivity()
                            .withDescr("Shipment created.")
                            .build())
                    .build();

        } else if (revisions.keySet().size() == 1) {
            final Revision firstRevision = revisions.keySet().iterator().next();
            final Shipment firstShipment = revisions.get(firstRevision);

            final ShipmentDifferenceDetector shipmentDifferenceDetector = new ShipmentDifferenceDetector();
            final List<ShipmentActivity> activities = shipmentDifferenceDetector.execute(firstShipment, shipment);

            return ShipmentActivityGroupBuilder
                    .aShipmentActivityGroup(modifiedBy,dateModified,shipment.getPrimaryId())
                    .withLines(activities)
                    .build();
        } else {
            final Iterator<Revision> iterator = revisions.keySet().iterator();

            final Revision lastModifiedRevision = iterator.next();
            final Shipment lastModifiedShipment = revisions.get(lastModifiedRevision);

            final ShipmentDifferenceDetector shipmentDifferenceDetector = new ShipmentDifferenceDetector();
            final List<ShipmentActivity> activities = shipmentDifferenceDetector.execute(lastModifiedShipment, shipment);

            return ShipmentActivityGroupBuilder
                    .aShipmentActivityGroup(modifiedBy,dateModified,shipment.getPrimaryId())
                    .withLines(activities)
                    .build();
        }
    }
}
