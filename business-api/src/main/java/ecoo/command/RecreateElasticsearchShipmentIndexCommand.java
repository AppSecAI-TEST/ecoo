package ecoo.command;

import ecoo.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Component
public class RecreateElasticsearchShipmentIndexCommand {

    private static final Logger LOG = LoggerFactory.getLogger(RecreateElasticsearchShipmentIndexCommand.class);

    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public RecreateElasticsearchShipmentIndexCommand(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public boolean execute() {
        LOG.info("--- execute ---");
        if (elasticsearchTemplate.indexExists(Shipment.class)) {
            elasticsearchTemplate.deleteIndex(Shipment.class);
        }
        if (elasticsearchTemplate.indexExists(ShipmentComment.class)) {
            elasticsearchTemplate.deleteIndex(ShipmentComment.class);
        }
        if (elasticsearchTemplate.indexExists(CommercialInvoice.class)) {
            elasticsearchTemplate.deleteIndex(CommercialInvoice.class);
        }
        if (elasticsearchTemplate.indexExists(CertificateOfOrigin.class)) {
            elasticsearchTemplate.deleteIndex(CertificateOfOrigin.class);
        }
        if (elasticsearchTemplate.indexExists(PackingList.class)) {
            elasticsearchTemplate.deleteIndex(PackingList.class);
        }
        return elasticsearchTemplate.createIndex(Shipment.class)
                && elasticsearchTemplate.createIndex(ShipmentComment.class)
                && elasticsearchTemplate.createIndex(CommercialInvoice.class)
                && elasticsearchTemplate.createIndex(CertificateOfOrigin.class)
                && elasticsearchTemplate.createIndex(PackingList.class);
    }
}
