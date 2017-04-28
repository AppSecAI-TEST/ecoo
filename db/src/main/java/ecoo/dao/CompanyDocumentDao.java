package ecoo.dao;

import ecoo.data.CompanyDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CompanyDocumentDao extends AuditLogDao<Integer, CompanyDocument> {

    /**
     * Returns the list of company documents for the given company.
     *
     * @param companyId The company pk.
     * @return A list.
     */
    List<CompanyDocument> findByCompany(Integer companyId);
}
