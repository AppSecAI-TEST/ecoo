package ecoo.service.impl;

import ecoo.dao.CompanyDocumentDao;
import ecoo.data.CompanyDocument;
import ecoo.service.CompanyDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcCompanyDocumentServiceImpl extends JdbcAuditTemplate<Integer, CompanyDocument, CompanyDocumentDao>
        implements CompanyDocumentService {

    @Autowired
    public JdbcCompanyDocumentServiceImpl(CompanyDocumentDao companyDocumentDao) {
        super(companyDocumentDao);
    }
}
