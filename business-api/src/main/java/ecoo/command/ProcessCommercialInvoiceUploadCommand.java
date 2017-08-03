package ecoo.command;

import ecoo.data.CommercialInvoice;
import ecoo.data.CommercialInvoiceLine;
import ecoo.data.upload.CommercialInvoiceUploadData;
import ecoo.data.upload.Upload;
import ecoo.service.CommercialInvoiceLineService;
import ecoo.service.CommercialInvoiceService;
import org.elasticsearch.common.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class ProcessCommercialInvoiceUploadCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessCommercialInvoiceUploadCommand.class);

    private CommercialInvoiceService commercialInvoiceService;

    private CommercialInvoiceLineService commercialInvoiceLineService;

    @Autowired
    public ProcessCommercialInvoiceUploadCommand(CommercialInvoiceService commercialInvoiceService, CommercialInvoiceLineService commercialInvoiceLineService) {
        this.commercialInvoiceService = commercialInvoiceService;
        this.commercialInvoiceLineService = commercialInvoiceLineService;
    }

    public void execute(Upload anUpload, CommercialInvoiceUploadData data) {
        Assert.notNull(anUpload, "The variable anUpload cannot be null.");
        Assert.notNull(anUpload.getShipmentId(), "The variable shipmentId cannot be null.");
        Assert.notNull(data, "The variable data cannot be null.");

        final CommercialInvoice commercialInvoice = commercialInvoiceService.findById(anUpload.getShipmentId());
        Assert.notNull(commercialInvoice, String.format("System cannot complete request. " +
                "No commercial invoice found for shipment %s.", anUpload.getShipmentId()));

        final CommercialInvoiceLine commercialInvoiceLine = new CommercialInvoiceLine();
        commercialInvoiceLine.setParentId(commercialInvoice.getPrimaryId());
        commercialInvoiceLine.setMarks(clean(data.getMarks()));
        commercialInvoiceLine.setProductCode(clean(data.getProductCode()));
        commercialInvoiceLine.setDescr(clean(data.getDescription()));
        commercialInvoiceLine.setOrigin(clean(data.getOrigin()));
        commercialInvoiceLine.setQty(new BigDecimal(data.getQuantity()));
        commercialInvoiceLine.setPrice(new BigDecimal(data.getPrice()));
        commercialInvoiceLine.setAmount(new BigDecimal(data.getAmount()));

        LOG.info(commercialInvoiceLine.toString());
        commercialInvoiceLineService.save(commercialInvoiceLine);
    }

    private String clean(String word) {
        word = StringUtils.stripToNull(word);
        return word == null ? null : StringUtils.trim(word).toUpperCase();
    }
}