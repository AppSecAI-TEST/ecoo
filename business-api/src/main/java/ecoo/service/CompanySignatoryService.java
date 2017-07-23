package ecoo.service;

import ecoo.data.CompanySignatory;
import ecoo.data.User;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface CompanySignatoryService extends CrudService<Integer, CompanySignatory>
        , AuditedModelAware<Integer, CompanySignatory> {

    /**
     * Adds a signatory to the given company.
     *
     * @param user      The user to add.
     * @param companyId The company to add the signatory to.
     * @return The new signatory.
     */
    CompanySignatory addSignatory(User user, Integer companyId);

    /**
     * Ends a signatory to the given company.
     *
     * @param user      The user to end.
     * @param companyId The company to add the signatory to.
     * @return The new signatory.
     */
    CompanySignatory endSignatory(User user, Integer companyId);

    /**
     * Returns the signatory for the given user and company.
     *
     * @param userId    The user to evaluate.
     * @param companyId The company to evaluate.
     * @return The signatory or null.
     */
    CompanySignatory findByUserAndCompany(Integer userId, Integer companyId);

    /**
     * Returns all the signatories for the given company.
     *
     * @param companyId The company to evaluate.
     * @return The list of signatories.
     */
    List<CompanySignatory> findByCompanyId(Integer companyId);
}
