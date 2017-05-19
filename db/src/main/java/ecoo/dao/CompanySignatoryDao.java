package ecoo.dao;

import ecoo.data.CompanySignatory;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface CompanySignatoryDao extends AuditLogDao<Integer, CompanySignatory> {


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
