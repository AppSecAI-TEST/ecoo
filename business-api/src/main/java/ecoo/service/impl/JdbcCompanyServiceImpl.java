package ecoo.service.impl;

import ecoo.dao.CompanyDao;
import ecoo.data.Company;
import ecoo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class JdbcCompanyServiceImpl extends JdbcAuditTemplate<Integer, Company, CompanyDao>
        implements CompanyService {

    @Autowired
    public JdbcCompanyServiceImpl(CompanyDao companyDao) {
        super(companyDao);
    }
}
