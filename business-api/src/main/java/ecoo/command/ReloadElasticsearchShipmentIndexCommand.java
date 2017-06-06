package ecoo.command;

import ecoo.dao.impl.es.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Component
public class ReloadElasticsearchShipmentIndexCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ReloadElasticsearchShipmentIndexCommand.class);

    private ShipmentElasticsearchIndexLoader shipmentElasticsearchIndexLoader;

    private ShipmentCommentElasticsearchIndexLoader shipmentCommentElasticsearchIndexLoader;

    private CommercialInvoiceElasticsearchIndexLoader commercialInvoiceElasticsearchIndexLoader;

    private CertificateOfOriginElasticsearchIndexLoader certificateOfOriginElasticsearchIndexLoader;

    private PackingListElasticsearchIndexLoader packingListElasticsearchIndexLoader;

    @Autowired
    public ReloadElasticsearchShipmentIndexCommand(ShipmentElasticsearchIndexLoader shipmentElasticsearchIndexLoader, ShipmentCommentElasticsearchIndexLoader shipmentCommentElasticsearchIndexLoader, CommercialInvoiceElasticsearchIndexLoader commercialInvoiceElasticsearchIndexLoader, CertificateOfOriginElasticsearchIndexLoader certificateOfOriginElasticsearchIndexLoader, PackingListElasticsearchIndexLoader packingListElasticsearchIndexLoader) {
        this.shipmentElasticsearchIndexLoader = shipmentElasticsearchIndexLoader;
        this.shipmentCommentElasticsearchIndexLoader = shipmentCommentElasticsearchIndexLoader;
        this.commercialInvoiceElasticsearchIndexLoader = commercialInvoiceElasticsearchIndexLoader;
        this.certificateOfOriginElasticsearchIndexLoader = certificateOfOriginElasticsearchIndexLoader;
        this.packingListElasticsearchIndexLoader = packingListElasticsearchIndexLoader;
    }

    public int execute() {
        LOG.info("--- execute ---");
        final int count = shipmentElasticsearchIndexLoader.loadAll().size();
        shipmentCommentElasticsearchIndexLoader.loadAll();
        commercialInvoiceElasticsearchIndexLoader.loadAll();
        certificateOfOriginElasticsearchIndexLoader.loadAll();
        packingListElasticsearchIndexLoader.loadAll();
        return count;
    }
}
