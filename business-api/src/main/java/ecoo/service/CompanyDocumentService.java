package ecoo.service;

import ecoo.data.CompanyDocument;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CompanyDocumentService extends CrudService<Integer, CompanyDocument>, AuditedModelAware<CompanyDocument> {

    /**
     * Returns the company document for the company and the type.
     *
     * @param companyId    The company id.
     * @param documentType The document type.
     * @return The company document or null.
     */
    CompanyDocument findByCompanyAndType(Integer companyId, String documentType);

    /**
     * Returns a list of company documents.
     *
     * @param companyId The company pk.
     * @return The list of documents.
     */
    List<CompanyDocument> findByCompany(Integer companyId);
}
