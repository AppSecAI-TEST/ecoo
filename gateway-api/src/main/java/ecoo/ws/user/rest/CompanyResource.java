package ecoo.ws.user.rest;

import ecoo.data.Company;
import ecoo.data.audit.Revision;
import ecoo.service.CompanyService;
import ecoo.ws.common.json.ValidationResponse;
import ecoo.ws.common.rest.BaseResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyResource extends BaseResource {

    private CompanyService companyService;

    @Autowired
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }


    @RequestMapping(value = "/validate/registrationNo", method = RequestMethod.GET)
    public ResponseEntity<ValidationResponse> validateRegistrationNumber(@RequestParam("id") Integer id
            , @RequestParam("registrationNo") String registrationNo) {
        registrationNo = StringUtils.trimToNull(registrationNo);
        if (StringUtils.isNotBlank(registrationNo)) {
            final Company company = companyService.findByRegistrationNo(registrationNo);
            if (company != null && !company.getPrimaryId().equals(id)) {
                return ResponseEntity.ok(new ValidationResponse(false, "Value is not unique"));
            } else {
                return ResponseEntity.ok(new ValidationResponse(true));
            }
        } else {
            return ResponseEntity.ok(new ValidationResponse(true));
        }
    }


    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Company> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Company> save(@RequestBody Company entity) {
        return ResponseEntity.ok(companyService.save(entity));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Company> delete(@RequestBody Company entity) {
        return ResponseEntity.ok(companyService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final Company entity = companyService.findById(id);
        return ResponseEntity.ok(companyService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final Company entity = companyService.findById(id);
        return ResponseEntity.ok(companyService.findModifiedBy(entity));
    }
}