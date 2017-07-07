package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.command.GenerateCommercialInvoicePdfCommand;
import ecoo.data.Shipment;
import ecoo.data.ShipmentDocument;
import ecoo.data.ShipmentDocumentType;
import ecoo.service.ShipmentDocumentService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class GenerateCommercialInvoiceTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(GenerateCommercialInvoiceTask.class);

    private GenerateCommercialInvoicePdfCommand generateCommercialInvoicePdfCommand;

    private ShipmentDocumentService shipmentDocumentService;

    @Autowired
    public GenerateCommercialInvoiceTask(GenerateCommercialInvoicePdfCommand generateCommercialInvoicePdfCommand, ShipmentDocumentService shipmentDocumentService) {
        this.generateCommercialInvoicePdfCommand = generateCommercialInvoicePdfCommand;
        this.shipmentDocumentService = shipmentDocumentService;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final File pdf = generateCommercialInvoicePdfCommand.execute(shipment);
        log.info("Commercial invoice pdf created @ {}", pdf.getAbsolutePath());

        final ShipmentDocument shipmentDocument = new ShipmentDocument();
        shipmentDocument.setDateCreated(new Date());

        shipmentDocument.setShipmentId(shipment.getPrimaryId());
        shipmentDocument.setDocumentType(ShipmentDocumentType.Type.ElectronicCommercialInvoice.id());

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

        shipmentDocumentService.save(shipmentDocument);
    }
}