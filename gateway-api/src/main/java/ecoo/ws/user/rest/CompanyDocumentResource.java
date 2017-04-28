

package ecoo.ws.user.rest;

import ecoo.data.audit.Revision;
import ecoo.data.CompanyDocument;
import ecoo.service.CompanyDocumentService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CompanyDocument> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(companyDocumentService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CompanyDocument> save(@RequestBody CompanyDocument entity) {
        return ResponseEntity.ok(companyDocumentService.save(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<CompanyDocument> delete(@RequestBody CompanyDocument entity) {
        return ResponseEntity.ok(companyDocumentService.delete(entity));
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