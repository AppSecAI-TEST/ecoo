

package ecoo.ws.rest;

import ecoo.command.CompressFileSetCommand;
import ecoo.data.ShipmentDocument;
import ecoo.data.audit.Revision;
import ecoo.service.ShipmentDocumentService;
import ecoo.ws.common.command.DownloadFileCommand;
import ecoo.ws.common.command.DownloadShipmentDocument;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.user.rest.json.CreateShipmentDocumentRequest;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/shipments/docs")
public class ShipmentDocumentResource extends BaseResource {

    private ShipmentDocumentService shipmentDocumentService;

    private CompressFileSetCommand compressFileSetCommand;

    @Autowired
    public ShipmentDocumentResource(ShipmentDocumentService shipmentDocumentService, CompressFileSetCommand compressFileSetCommand) {
        this.shipmentDocumentService = shipmentDocumentService;
        this.compressFileSetCommand = compressFileSetCommand;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/shipment/{shipmentId}/download", method = RequestMethod.GET)
    public void compressAndDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer shipmentId) throws IOException, ZipException {
        final ZipFile zipFile = compressFileSetCommand.execute(shipmentId);

        final DownloadFileCommand downloadFileCommand = new DownloadFileCommand();
        downloadFileCommand.execute(response, request.getServletContext(), zipFile.getFile().getAbsolutePath());
    }


    @SuppressWarnings("unused")
    @RequestMapping(value = "/id/{id}/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws IOException {
        final ShipmentDocument shipmentDocument = shipmentDocumentService.findById(id);

        final DownloadShipmentDocument downloadShipmentDocument = new DownloadShipmentDocument();
        downloadShipmentDocument.execute(response, shipmentDocument);
    }

    @RequestMapping(value = "/shipmentId/{shipmentId}", method = RequestMethod.GET)
    public ResponseEntity<List<ShipmentDocument>> findByShipment(@PathVariable Integer shipmentId) {
        return ResponseEntity.ok(shipmentDocumentService.findByShipment(shipmentId));
    }

    @RequestMapping(value = "/shipmentId/{shipmentId}/docType/{documentType}", method = RequestMethod.GET)
    public ResponseEntity<ShipmentDocument> findByShipmentAndType(@PathVariable Integer shipmentId
            , @PathVariable String documentType) {
        return ResponseEntity.ok(shipmentDocumentService.findByShipmentAndType(shipmentId, documentType));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ShipmentDocument> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(shipmentDocumentService.findById(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ShipmentDocument> create(@RequestBody CreateShipmentDocumentRequest createShipmentDocumentRequest) throws IOException {
        ShipmentDocument shipmentDocument = shipmentDocumentService.findById(createShipmentDocumentRequest.getPrimaryId());
        if (shipmentDocument == null) {
            shipmentDocument = shipmentDocumentService.findByShipmentAndType(createShipmentDocumentRequest.getShipmentId()
                    , createShipmentDocumentRequest.getDocumentType());
            if (shipmentDocument == null) {
                shipmentDocument = new ShipmentDocument();
                shipmentDocument.setDateCreated(new Date());
            }
        }
        shipmentDocument.setShipmentId(createShipmentDocumentRequest.getShipmentId());
        shipmentDocument.setDocumentType(createShipmentDocumentRequest.getDocumentType());

        final File srcFile = new File(createShipmentDocumentRequest.getFileName());
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
        shipmentDocument.setMimeType(createShipmentDocumentRequest.getMimeType());
        shipmentDocument.setSizeInKb(createShipmentDocumentRequest.getSizeInKb());

        return ResponseEntity.ok(shipmentDocumentService.save(shipmentDocument));
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ShipmentDocument> save(@RequestBody ShipmentDocument entity) {
        return ResponseEntity.ok(shipmentDocumentService.save(entity));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ShipmentDocument> delete(@PathVariable Integer id) {
        final ShipmentDocument entity = shipmentDocumentService.findById(id);
        return ResponseEntity.ok(shipmentDocumentService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final ShipmentDocument entity = shipmentDocumentService.findById(id);
        return ResponseEntity.ok(shipmentDocumentService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final ShipmentDocument entity = shipmentDocumentService.findById(id);
        return ResponseEntity.ok(shipmentDocumentService.findModifiedBy(entity));
    }
}