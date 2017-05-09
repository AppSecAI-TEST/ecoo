package ecoo.validator;

import ecoo.data.Shipment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Component
public class ShipmentValidator {

    public void validate(Shipment shipment) {
        Assert.notNull(shipment, "System cannot complete request. The variable shipment cannot be null.");
        Assert.hasText(shipment.getExporterReference(), "System cannot complete request. " +
                "The exporter reference is required.");
    }
}
