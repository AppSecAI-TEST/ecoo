package ecoo.audit;

import ecoo.data.Shipment;
import ecoo.data.ShipmentActivity;
import ecoo.data.ShipmentActivityBuilder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class ShipmentDifferenceDetector {

    public List<ShipmentActivity> execute(final Shipment firstShipment, final Shipment secondShipment) {
        Assert.notNull(firstShipment, "The variable firstShipment cannot be null.");
        Assert.notNull(secondShipment, "The variable secondShipment cannot be null.");

        final List<ShipmentActivity> activities = new ArrayList<>();

        buildActivityIfDifferent("Export reference", firstShipment.getExporterReference()
                , secondShipment.getExporterReference(), activities);

        buildActivityIfDifferent("Export invoice number", firstShipment.getExportInvoiceNumber()
                , secondShipment.getExportInvoiceNumber(), activities);

        buildActivityIfDifferent("Export invoice date", firstShipment.getExportInvoiceDate()
                , secondShipment.getExportInvoiceDate(), activities);

        return activities;
    }

    private void buildActivityIfDifferent(String attributeName, Object var1, Object var2
            , List<ShipmentActivity> activities) {
        if (var1 != null && var2 == null) {
            activities.add(ShipmentActivityBuilder.aShipmentActivity()
                    .withDescr(String.format("%s <%s> removed.", attributeName, var1))
                    .build());
        } else if (var1 == null && var2 != null) {
            activities.add(ShipmentActivityBuilder.aShipmentActivity()
                    .withDescr(String.format("%s <%s> added.", attributeName, var2))
                    .build());

        } else if (var1 != null && !var1.equals(var2)) {
            activities.add(ShipmentActivityBuilder.aShipmentActivity()
                    .withDescr(String.format("%s updated from <%s> to <%s>.", attributeName, var1, var2))
                    .build());
        }
    }
}
