package ecoo.ws.user.rest;

import ecoo.data.CompanySignatory;
import ecoo.data.audit.Revision;
import ecoo.service.CompanySignatoryService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@RestController
@RequestMapping("/api/companies/signatories")
public class CompanySignatoryResource extends BaseResource {

    private CompanySignatoryService companySignatoryService;

    @Autowired
    public CompanySignatoryResource(CompanySignatoryService companySignatoryService) {
        this.companySignatoryService = companySignatoryService;
    }

    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<List<CompanySignatory>> findByCompany(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companySignatoryService.findByCompanyId(companyId));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<CompanySignatory> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(companySignatoryService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CompanySignatory> save(@RequestBody CompanySignatory entity) {
        return ResponseEntity.ok(companySignatoryService.save(entity));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CompanySignatory> delete(@PathVariable Integer id) {
        final CompanySignatory entity = companySignatoryService.findById(id);
        return ResponseEntity.ok(companySignatoryService.delete(entity));
    }

    @RequestMapping(value = "/end/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<CompanySignatory> endSignatory(@PathVariable Integer id) {
        final CompanySignatory entity = companySignatoryService.findById(id);
        return ResponseEntity.ok(companySignatoryService.endSignatory(entity.getUser(), entity.getCompanyId()));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final CompanySignatory entity = companySignatoryService.findById(id);
        return ResponseEntity.ok(companySignatoryService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final CompanySignatory entity = companySignatoryService.findById(id);
        return ResponseEntity.ok(companySignatoryService.findModifiedBy(entity));
    }
}