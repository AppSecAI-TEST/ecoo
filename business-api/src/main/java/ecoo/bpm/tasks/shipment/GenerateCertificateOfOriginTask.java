package ecoo.bpm.tasks.shipment;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.NewShipmentRequest;
import ecoo.data.Shipment;
import ecoo.service.ReportService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class GenerateCertificateOfOriginTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(GenerateCertificateOfOriginTask.class);

    private ReportService reportService;

    @Autowired
    public GenerateCertificateOfOriginTask(ReportService reportService) {
        this.reportService = reportService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final NewShipmentRequest request = (NewShipmentRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Shipment shipment = request.getShipment();

        final File invoicePdf = callAndSaveSSRSReport(shipment);
        log.info("Certificate Of Origin pdf created @ {}", invoicePdf.getAbsolutePath());
    }


    private File callAndSaveSSRSReport(Shipment shipment) throws IOException {
        final Map<String, String> reportParameters = new HashMap<>();
        reportParameters.put("userSignatureId", "1");
        reportParameters.put("shipmentId", shipment.getPrimaryId().toString());

        final byte[] content = reportService.execute("/ECOO/CertificateOfOrigin"
                , reportParameters);

        // C:\Users\Justin\.lg\docs\invoices\1
        final String path = "C:\\Users\\Justin\\.ecoo\\temp\\coo";
        final File targetDir = new File(path);
        if (!targetDir.exists() && targetDir.mkdirs()) {
            log.info(String.format("Directory %s created.", targetDir.getAbsolutePath()));
        }

        final String fileName = "coo-" + shipment.getPrimaryId() + ".pdf";
        final File pdf = new File(targetDir.getAbsolutePath(), fileName);

        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(pdf));
            FileCopyUtils.copy(content, stream);

        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            if (stream != null) stream.close();
        }
        return pdf;
    }
}