package ecoo.service.impl;

import ecoo.dao.CompanyDocumentDao;
import ecoo.data.CompanyDocument;
import ecoo.service.CompanyDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcCompanyDocumentServiceImpl extends JdbcAuditTemplate<Integer, CompanyDocument, CompanyDocumentDao>
        implements CompanyDocumentService {

    private CompanyDocumentDao companyDocumentDao;

    @Autowired
    public JdbcCompanyDocumentServiceImpl(CompanyDocumentDao companyDocumentDao) {
        super(companyDocumentDao);
        this.companyDocumentDao = companyDocumentDao;
    }

    /**
     * Returns the company document for the company and the type.
     *
     * @param companyId    The company id.
     * @param documentType The document type.
     * @return The company document or null.
     */
    @Override
    public CompanyDocument findByCompanyAndType(Integer companyId, String documentType) {
        return companyDocumentDao.findByCompanyAndType(companyId, documentType);
    }

    /**
     * Returns a list of company documents.
     *
     * @param companyId The company pk.
     * @return The list of documents.
     */
    @Override
    public List<CompanyDocument> findByCompany(Integer companyId) {
        return companyDocumentDao.findByCompany(companyId);
    }
}
