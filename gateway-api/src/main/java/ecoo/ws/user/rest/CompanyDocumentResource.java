

package ecoo.ws.user.rest;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.data.CompanyDocument;
import ecoo.data.DocumentTypes;
import ecoo.data.Role;
import ecoo.data.audit.Revision;
import ecoo.service.CompanyDocumentService;
import ecoo.ws.common.command.DownloadCompanyDocument;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.user.rest.json.CreateCompanyDocumentRequest;
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
 * @since April 2017
 */
@RestController
@RequestMapping("/api/companies/docs")
public class CompanyDocumentResource extends BaseResource {

    private CompanyDocumentService companyDocumentService;

    @Autowired
    public CompanyDocumentResource(CompanyDocumentService companyDocumentService) {
        this.companyDocumentService = companyDocumentService;
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/id/{id}/download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable Integer id) throws IOException {
        final CompanyDocument companyDocument = companyDocumentService.findById(id);

        final DownloadCompanyDocument downloadCompanyDocument = new DownloadCompanyDocument();
        downloadCompanyDocument.execute(response, companyDocument);
    }

    @RequestMapping(value = "/companyId/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<List<CompanyDocument>> findByCompany(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companyDocumentService.findByCompany(companyId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CompanyDocument> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(companyDocumentService.findById(id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<CompanyDocument> create(@RequestBody CreateCompanyDocumentRequest createCompanyDocumentRequest) throws IOException {
        CompanyDocument companyDocument = companyDocumentService.findById(createCompanyDocumentRequest.getPrimaryId());
        if (companyDocument == null) {
            companyDocument = companyDocumentService.findByCompanyAndType(createCompanyDocumentRequest.getCompanyId()
                    , createCompanyDocumentRequest.getDocumentType());
            if (companyDocument == null) {
                companyDocument = new CompanyDocument();
                companyDocument.setDateCreated(new Date());
            }
        }
        companyDocument.setCompanyId(createCompanyDocumentRequest.getCompanyId());
        companyDocument.setDocumentType(createCompanyDocumentRequest.getDocumentType());

        final File srcFile = new File(createCompanyDocumentRequest.getFileName());
        companyDocument.setFileName(srcFile.getName());

        FileInputStream fis = null;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            fis = new FileInputStream(srcFile);

            byte[] buffer = new byte[102400];
            int n;
            while (-1 != (n = fis.read(buffer))) {
                out.write(buffer, 0, n);
            }
            companyDocument.setEncodedImage(Base64.getEncoder().encodeToString(out.toByteArray()));
        } finally {
            out.close();
            if (fis != null) fis.close();
        }
        companyDocument.setMimeType(createCompanyDocumentRequest.getMimeType());
        companyDocument.setSizeInKb(createCompanyDocumentRequest.getSizeInKb());

        return ResponseEntity.ok(companyDocumentService.save(companyDocument));
    }


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CompanyDocument> save(@RequestBody CompanyDocument entity) {
        return ResponseEntity.ok(companyDocumentService.save(entity));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CompanyDocument> delete(@PathVariable Integer id) {
        final CompanyDocument entity = companyDocumentService.findById(id);
        if (entity == null) {
            return ResponseEntity.ok(null);
        } else {
            if (entity.isDocumentType(DocumentTypes.ProofOfCompanyRegistration)) {
                if (currentUser().isInRole(Role.ROLE_SYSADMIN)) {
                    return ResponseEntity.ok(companyDocumentService.delete(entity));
                } else {
                    throw new BusinessRuleViolationException(String.format("System cannot delete document. " +
                            "You do not have permission to delete the proof of company registration document " +
                            "%s.", entity.getPrimaryId()));
                }
            } else {
                return ResponseEntity.ok(companyDocumentService.delete(entity));
            }
        }
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final CompanyDocument entity = companyDocumentService.findById(id);
        return ResponseEntity.ok(companyDocumentService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final CompanyDocument entity = companyDocumentService.findById(id);
        return ResponseEntity.ok(companyDocumentService.findModifiedBy(entity));
    }
}