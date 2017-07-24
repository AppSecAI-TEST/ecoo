package ecoo.audit;

import ecoo.data.Shipment;
import ecoo.data.ShipmentActivity;
import ecoo.data.ShipmentActivityBuilder;
import ecoo.data.ShipmentStatus;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
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

        buildActivityIfDifferent("Buyer reference", firstShipment.getBuyerReference()
                , secondShipment.getBuyerReference(), activities);

        buildActivityIfDifferent("Buyer order date", firstShipment.getBuyerOrderDate()
                , secondShipment.getBuyerOrderDate(), activities);

        buildActivityIfDifferent("Letter of credit number", firstShipment.getLetterOfCreditNumber()
                , secondShipment.getLetterOfCreditNumber(), activities);

        buildActivityIfDifferent("UCR number", firstShipment.getUcrNumber()
                , secondShipment.getUcrNumber(), activities);

        buildActivityIfDifferent("Exporter name", firstShipment.getExporterName()
                , secondShipment.getExporterName(), activities);

        buildActivityIfDifferent("Exporter building", firstShipment.getExporterBuilding()
                , secondShipment.getExporterBuilding(), activities);

        buildActivityIfDifferent("Exporter street", firstShipment.getExporterStreet()
                , secondShipment.getExporterStreet(), activities);

        buildActivityIfDifferent("Exporter city", firstShipment.getExporterCity()
                , secondShipment.getExporterCity(), activities);

        buildActivityIfDifferent("Exporter postcode", firstShipment.getExporterPostcode()
                , secondShipment.getExporterPostcode(), activities);

        buildActivityIfDifferent("Exporter province", firstShipment.getExporterProvince()
                , secondShipment.getExporterProvince(), activities);

        buildActivityIfDifferent("Exporter country", firstShipment.getExporterCountry()
                , secondShipment.getExporterCountry(), activities);

        buildActivityIfDifferent("Consignee name", firstShipment.getConsigneeName()
                , secondShipment.getConsigneeName(), activities);

        buildActivityIfDifferent("Consignee building", firstShipment.getConsigneeBuilding()
                , secondShipment.getConsigneeBuilding(), activities);

        buildActivityIfDifferent("Consignee street", firstShipment.getConsigneeStreet()
                , secondShipment.getConsigneeStreet(), activities);

        buildActivityIfDifferent("Consignee city", firstShipment.getConsigneeCity()
                , secondShipment.getConsigneeCity(), activities);

        buildActivityIfDifferent("Consignee postcode", firstShipment.getConsigneePostcode()
                , secondShipment.getConsigneePostcode(), activities);

        buildActivityIfDifferent("Consignee province", firstShipment.getConsigneeProvince()
                , secondShipment.getConsigneeProvince(), activities);

        buildActivityIfDifferent("Consignee country", firstShipment.getConsigneeCountry()
                , secondShipment.getConsigneeCountry(), activities);

        buildActivityIfDifferent("Buyer name", firstShipment.getBuyerName()
                , secondShipment.getBuyerName(), activities);

        buildActivityIfDifferent("Buyer building", firstShipment.getBuyerBuilding()
                , secondShipment.getBuyerBuilding(), activities);

        buildActivityIfDifferent("Buyer street", firstShipment.getBuyerStreet()
                , secondShipment.getBuyerStreet(), activities);

        buildActivityIfDifferent("Buyer city", firstShipment.getBuyerCity()
                , secondShipment.getBuyerCity(), activities);

        buildActivityIfDifferent("Buyer postcode", firstShipment.getBuyerPostcode()
                , secondShipment.getBuyerPostcode(), activities);

        buildActivityIfDifferent("Buyer province", firstShipment.getBuyerProvince()
                , secondShipment.getBuyerProvince(), activities);

        buildActivityIfDifferent("Buyer country", firstShipment.getBuyerCountry()
                , secondShipment.getBuyerCountry(), activities);

        buildActivityIfDifferent("Transport type", firstShipment.getTransportTypeId()
                , secondShipment.getTransportTypeId(), activities);

        buildActivityIfDifferent("Port of loading", firstShipment.getPortOfLoading()
                , secondShipment.getPortOfLoading(), activities);

        buildActivityIfDifferent("Port of acceptance", firstShipment.getPortOfAcceptance()
                , secondShipment.getPortOfAcceptance(), activities);

        buildActivityIfDifferent("Base currency", firstShipment.getCurrency()
                , secondShipment.getCurrency(), activities);

        final ShipmentStatus firstShipmentStatus = ShipmentStatus.valueOfById(firstShipment.getStatus());
        final ShipmentStatus secondShipmentStatus = ShipmentStatus.valueOfById(secondShipment.getStatus());
        buildActivityIfDifferent("Status", firstShipmentStatus, secondShipmentStatus, activities);

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

        } else if (var1 instanceof Date) {
            final String date1 = DateTime.now().withMillis(((Date) var1).getTime()).toString("yyyy/MM/dd HH:mm:ss");
            final String date2 = DateTime.now().withMillis(((Date) var2).getTime()).toString("yyyy/MM/dd HH:mm:ss");
            if (!date1.equals(date2)) {
                activities.add(ShipmentActivityBuilder.aShipmentActivity()
                        .withDescr(String.format("%s updated from <%s> to <%s>.", attributeName, date1, date2))
                        .build());
            }
        } else if (var1 instanceof ShipmentStatus) {
            ShipmentStatus shipmentStatus1 = (ShipmentStatus) var1;
            ShipmentStatus shipmentStatus2 = (ShipmentStatus) var2;
            if (!shipmentStatus1.equals(shipmentStatus2)) {
                activities.add(ShipmentActivityBuilder.aShipmentActivity()
                        .withDescr(String.format("%s updated from <%s> to <%s>.", attributeName, shipmentStatus1.description()
                                , shipmentStatus2.description()))
                        .build());
            }
        } else if (var1 instanceof String) {
            final String string1 = StringUtils.stripToNull((String) var1);
            final String string2 = StringUtils.stripToNull((String) var2);
            if (string1 != null && string2 == null) {
                activities.add(ShipmentActivityBuilder.aShipmentActivity()
                        .withDescr(String.format("%s <%s> removed.", attributeName, var1))
                        .build());
            } else if (string1 == null && string2 != null) {
                activities.add(ShipmentActivityBuilder.aShipmentActivity()
                        .withDescr(String.format("%s <%s> added.", attributeName, var2))
                        .build());

            } else if (string1 != null && !string1.equals(string2)) {
                activities.add(ShipmentActivityBuilder.aShipmentActivity()
                        .withDescr(String.format("%s updated from <%s> to <%s>.", attributeName, string1, string2))
                        .build());
            }

        } else if (var1 != null && !var1.equals(var2)) {
            activities.add(ShipmentActivityBuilder.aShipmentActivity()
                    .withDescr(String.format("%s updated from <%s> to <%s>.", attributeName, var1, var2))
                    .build());
        }
    }
}
