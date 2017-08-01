package ecoo.ws.user.rest;

import ecoo.data.ChamberDocument;
import ecoo.data.audit.Revision;
import ecoo.service.ChamberDocumentService;
import ecoo.ws.common.command.DownloadDocument;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RestController
@RequestMapping("/api/chambers/docs")
public class ChamberDocumentResource extends BaseResource {

    private ChamberDocumentService chamberDocumentService;

    @Autowired
    public ChamberDocumentResource(ChamberDocumentService chamberDocumentService) {
        this.chamberDocumentService = chamberDocumentService;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/id/{id}/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws IOException {
        final ChamberDocument chamberDocument = chamberDocumentService.findById(id);

        final DownloadDocument downloadDocument = new DownloadDocument();
        downloadDocument.execute(response, chamberDocument.getEncodedImage(), chamberDocument.getMimeType()
                , chamberDocument.getFileName());
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/chamber/{chamberId}/documentType/{documentType}/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer chamberId
            , @PathVariable String documentType) throws IOException {
        final ChamberDocument chamberDocument = chamberDocumentService.findByChamberAndType(chamberId,documentType);

        final DownloadDocument downloadDocument = new DownloadDocument();
        downloadDocument.execute(response, chamberDocument.getEncodedImage(), chamberDocument.getMimeType()
                , chamberDocument.getFileName());
    }

    @RequestMapping(value = "/chamberId/{chamberId}", method = RequestMethod.GET)
    public ResponseEntity<List<ChamberDocument>> findByChamber(@PathVariable Integer chamberId) {
        return ResponseEntity.ok(chamberDocumentService.findByChamber(chamberId));
    }

    @RequestMapping(value = "/chamberId/{chamberId}/type/{type}", method = RequestMethod.GET)
    public ResponseEntity<ChamberDocument> findByChamberAndType(@PathVariable Integer chamberId
            , @PathVariable String type) {
        return ResponseEntity.ok(chamberDocumentService.findByChamberAndType(chamberId, type));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<ChamberDocument> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chamberDocumentService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ChamberDocument> save(@RequestBody ChamberDocument entity) {
        return ResponseEntity.ok(chamberDocumentService.save(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final ChamberDocument entity = chamberDocumentService.findById(id);
        return ResponseEntity.ok(chamberDocumentService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final ChamberDocument entity = chamberDocumentService.findById(id);
        return ResponseEntity.ok(chamberDocumentService.findModifiedBy(entity));
    }
}