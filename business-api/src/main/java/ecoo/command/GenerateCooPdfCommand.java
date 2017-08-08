package ecoo.command;

import ecoo.service.FeatureService;
import ecoo.service.ReportService;
import ecoo.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class GenerateCooPdfCommand {

    private static final Logger LOG = LoggerFactory.getLogger(GenerateCooPdfCommand.class);

    private ReportService reportService;

    private FeatureService featureService;

    @Autowired
    public GenerateCooPdfCommand(ReportService reportService, FeatureService featureService) {
        this.reportService = reportService;
        this.featureService = featureService;
    }

    @SuppressWarnings("Duplicates")
    public File execute(Integer shipmentId) throws IOException {
        final Map<String, String> reportParameters = new HashMap<>();
        reportParameters.put("shipmentId", shipmentId.toString());

        final byte[] content = reportService.execute("/ECOO/CertificateOfOrigin"
                , reportParameters);

        // C:\Users\Justin\.ecoo\temp\coo
        final String path = FileUtils.resolveDir(featureService.tempDirectory()) + "docs";
        final File targetDir = new File(path);
        if (!targetDir.exists() && targetDir.mkdirs()) {
            LOG.info(String.format("Directory %s created.", targetDir.getAbsolutePath()));
        }

        final String fileName = "CertificateOfOrigin-" + shipmentId + ".pdf";
        final File pdf = new File(targetDir.getAbsolutePath(), fileName);

        BufferedOutputStream stream = null;
        try {
            stream = new BufferedOutputStream(new FileOutputStream(pdf));
            stream.write(content);

        } catch (final IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            if (stream != null) stream.close();
        }
        return pdf;
    }
}
