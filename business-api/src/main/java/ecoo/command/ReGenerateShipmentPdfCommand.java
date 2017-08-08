package ecoo.command;

import ecoo.data.ShipmentDocument;
import ecoo.data.ShipmentDocumentType;
import ecoo.service.ShipmentDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since August 2017
 */
@Component
public class ReGenerateShipmentPdfCommand {

    private final Logger log = LoggerFactory.getLogger(ReGenerateShipmentPdfCommand.class);

    private GenerateCommercialInvoicePdfCommand generateCommercialInvoicePdfCommand;

    private GenerateCooPdfCommand generateCooPdfCommand;

    private ShipmentDocumentService shipmentDocumentService;

    @Autowired
    public ReGenerateShipmentPdfCommand(GenerateCommercialInvoicePdfCommand generateCommercialInvoicePdfCommand, GenerateCooPdfCommand generateCooPdfCommand, ShipmentDocumentService shipmentDocumentService) {
        this.generateCommercialInvoicePdfCommand = generateCommercialInvoicePdfCommand;
        this.generateCooPdfCommand = generateCooPdfCommand;
        this.shipmentDocumentService = shipmentDocumentService;
    }

    public void execute(Integer shipmentId) throws IOException {
        buildAndSaveCommercialInvoicePdf(shipmentId);
        buildAndSaveCertificateOfOriginPdf(shipmentId);
    }

    private void buildAndSaveCommercialInvoicePdf(Integer shipmentId) throws IOException {
        final File pdf = generateCommercialInvoicePdfCommand.execute(shipmentId);
        log.info("Commercial invoice pdf created @ {}", pdf.getAbsolutePath());

        ShipmentDocument commercialInvoice = shipmentDocumentService.findByShipmentAndType(shipmentId
                , ShipmentDocumentType.Type.ElectronicCommercialInvoice.id());
        if (commercialInvoice == null) {
            commercialInvoice = new ShipmentDocument();
            commercialInvoice.setDateCreated(new Date());

            commercialInvoice.setShipmentId(shipmentId);
            commercialInvoice.setDocumentType(ShipmentDocumentType.Type.ElectronicCommercialInvoice.id());
        }

        putImage(pdf, commercialInvoice);
        shipmentDocumentService.save(commercialInvoice);
        log.info("Generated Commercial Invoice .................. SUCCESS");
    }

    private void buildAndSaveCertificateOfOriginPdf(Integer shipmentId) throws IOException {
        final File pdf = generateCooPdfCommand.execute(shipmentId);
        log.info("Certificate of origin pdf created @ {}", pdf.getAbsolutePath());

        ShipmentDocument coo = shipmentDocumentService.findByShipmentAndType(shipmentId
                , ShipmentDocumentType.Type.ElectronicCertificateOfOrigin.id());
        if (coo == null) {
            coo = new ShipmentDocument();
            coo.setDateCreated(new Date());

            coo.setShipmentId(shipmentId);
            coo.setDocumentType(ShipmentDocumentType.Type.ElectronicCertificateOfOrigin.id());
        }

        putImage(pdf, coo);
        shipmentDocumentService.save(coo);
        log.info("Generated Certificate of Origin ............... SUCCESS");
    }

    @SuppressWarnings("Duplicates")
    private void putImage(File pdf, ShipmentDocument shipmentDocument) throws IOException {
        final File srcFile = new File(pdf.getAbsolutePath());
        shipmentDocument.setFileName(srcFile.getName());

        FileInputStream fis = null;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(srcFile);

            byte[] buffer = new byte[102400];
            int n;
            while (-1 != (n = fis.read(buffer))) {
                out.write(buffer, 0, n);
            }
            shipmentDocument.setData(Base64.getEncoder().encodeToString(out.toByteArray()));
        } finally {
            out.close();
            if (fis != null) fis.close();
        }

        String mimeType = URLConnection.guessContentTypeFromName(srcFile.getAbsolutePath());
        if (mimeType == null) {
            mimeType = "unknown";
        }
        shipmentDocument.setMimeType(mimeType);

        final long kilobytes = srcFile.length() / 1024;
        shipmentDocument.setSizeInKb(kilobytes);
    }
}
