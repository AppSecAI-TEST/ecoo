package ecoo.command;

import ecoo.convert.ShipmentDocumentToFileCommand;
import ecoo.data.ShipmentDocument;
import ecoo.service.FeatureService;
import ecoo.service.ShipmentDocumentService;
import ecoo.util.FileUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Component
public class CompressFileSetCommand {

    private static final Logger LOG = LoggerFactory.getLogger(CompressFileSetCommand.class);

    private ShipmentDocumentService shipmentDocumentService;

    private FeatureService featureService;

    @Autowired
    public CompressFileSetCommand(ShipmentDocumentService shipmentDocumentService, FeatureService featureService) {
        this.shipmentDocumentService = shipmentDocumentService;
        this.featureService = featureService;
    }

    public ZipFile execute(Integer shipmentId) throws IOException, ZipException {
        final String tempDir = tempDir();

        final String zipFileName = determineZipFileName(tempDir, shipmentId);
        final ZipFile zipFile = new ZipFile(zipFileName);

        final ArrayList srcFiles = makeSourceFiles(tempDir, shipmentId);
        final ZipParameters parameters = createParameters();

        zipFile.addFiles(srcFiles, parameters);
        LOG.info("Zip file created: {}", zipFileName);

        return zipFile;
    }

    @SuppressWarnings({"ConstantConditions", "UseBulkOperation", "unchecked"})
    private ArrayList makeSourceFiles(String tempDir, Integer shipmentId) throws IOException {
        final List<ShipmentDocument> shipmentDocuments = shipmentDocumentService.findByShipment(shipmentId);

        final String src = FileUtils.resolveDir(tempDir) + "src";
        for (ShipmentDocument shipmentDocument : shipmentDocuments) {
            createAndStoreTemporaryFile(src, shipmentDocument);
        }

        final File srcDir = new File(src);
        final ArrayList srcFiles = new ArrayList();
        for (final File srcFile : srcDir.listFiles()) {
            srcFiles.add(srcFile);
        }
        return srcFiles;
    }

    private void createAndStoreTemporaryFile(String srcDir, ShipmentDocument shipmentDocument) throws IOException {
        final File targetDir = new File(srcDir);
        if (!targetDir.exists() && targetDir.mkdirs()) {
            LOG.info(String.format("Directory %s created.", targetDir.getAbsolutePath()));
        }

        final File srcFile = new File(targetDir.getAbsolutePath(), shipmentDocument.getFileName());
        LOG.info(srcFile.getAbsolutePath());

        final ShipmentDocumentToFileCommand shipmentDocumentToFileCommand = new ShipmentDocumentToFileCommand();
        shipmentDocumentToFileCommand.execute(shipmentDocument, srcDir);
    }


    private String determineZipFileName(String tempDir, Integer shipmentId) {
        return FileUtils.resolveDir(tempDir) + "shipment-" + shipmentId + ".zip";
    }

    private String tempDir() {
        final String tempDir = featureService.tempDirectory();
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        return FileUtils.resolveDir(tempDir) + uuid;
    }

    private ZipParameters createParameters() {
        final ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        // DEVNOTE: Removed because InfoSlips automated run cannot handle passwords.
        // Set the encryption flag to true
        // If this is set to false, then the rest of encryption properties are ignored
        parameters.setEncryptFiles(false);

//        // Set the encryption method to Standard Zip Encryption
//        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//
//        parameters.setPassword("password");
        return parameters;
    }
}
