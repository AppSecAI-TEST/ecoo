package ecoo.command;

import ecoo.data.CertificateOfOrigin;
import ecoo.data.CertificateOfOriginLine;
import ecoo.data.upload.CertificateOfOriginUploadData;
import ecoo.data.upload.Upload;
import ecoo.service.CertificateOfOriginService;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Justin Rundle
 * @since August 2017
 */
@Component
public class ProcessCertificateOfOriginUploadCommand {

    private CertificateOfOriginService certificateOfOriginService;

    @Autowired
    public ProcessCertificateOfOriginUploadCommand(CertificateOfOriginService certificateOfOriginService) {
        this.certificateOfOriginService = certificateOfOriginService;
    }

    public void execute(Upload anUpload, Collection<CertificateOfOriginUploadData> data) {
        Assert.notNull(anUpload, "The variable anUpload cannot be null.");
        Assert.notNull(anUpload.getShipmentId(), "The variable shipmentId cannot be null.");
        Assert.notNull(data, "The variable data cannot be null.");

        final CertificateOfOrigin certificateOfOrigin = certificateOfOriginService.findById(anUpload.getShipmentId());
        Assert.notNull(certificateOfOrigin, String.format("System cannot complete request. " +
                "No certificate of origin found for shipment %s.", anUpload.getShipmentId()));

        for (CertificateOfOriginUploadData d : data) {
            final CertificateOfOriginLine certificateOfOriginLine = new CertificateOfOriginLine();
            certificateOfOriginLine.setParentId(certificateOfOrigin.getPrimaryId());
            certificateOfOriginLine.setMarks(clean(d.getMarks()));
            certificateOfOriginLine.setProductCode(clean(d.getProductCode()));
            certificateOfOriginLine.setDescr(clean(d.getDescription()));
            certificateOfOriginLine.setOrigin(clean(d.getOrigin()));
            certificateOfOriginLine.setQty(new BigDecimal(d.getQuantity()));
            certificateOfOriginLine.setPrice(new BigDecimal(d.getPrice()));
            certificateOfOriginLine.setAmount(new BigDecimal(d.getAmount()));
            certificateOfOriginLine.setPriceStated(true);
            certificateOfOrigin.getLines().add(certificateOfOriginLine);
        }
        certificateOfOriginService.save(certificateOfOrigin);
    }

    private String clean(String word) {
        word = StringUtils.stripToNull(word);
        return word == null ? null : StringUtils.trim(word).toUpperCase();
    }
}