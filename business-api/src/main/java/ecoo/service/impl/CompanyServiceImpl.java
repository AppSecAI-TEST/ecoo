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
public class CompanyServiceImpl extends AuditTemplate<Integer, Company, CompanyDao> implements CompanyService {

    private CompanyDao companyDao;

    @Autowired
    public CompanyServiceImpl(CompanyDao companyDao) {
        super(companyDao);
        this.companyDao = companyDao;
    }

    @Override
    public Company findByRegistrationNo(String registrationNo) {
        return companyDao.findByRegistrationNo(registrationNo);
    }
}
