package ecoo.command;

import ecoo.dao.impl.es.ShipmentElasticsearchRepository;
import ecoo.data.Role;
import ecoo.data.Shipment;
import ecoo.data.ShipmentStatus;
import ecoo.data.User;
import ecoo.service.ShipmentService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class ShipmentActivityBuilder {

    public void build(Shipment shipment) {
        Assert.notNull(shipment,"The variable shipment cannot be null.");
    }
}
