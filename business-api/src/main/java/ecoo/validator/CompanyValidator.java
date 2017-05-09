package ecoo.validator;

import ecoo.dao.CompanyDao;
import ecoo.data.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class CompanyValidator {

    private CompanyDao companyDao;

    @Autowired
    public CompanyValidator(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public void validate(Company company) {
        Assert.notNull(company, "System cannot complete request. The variable company cannot be null.");
        Assert.hasText(company.getRegistrationNo(), "System cannot complete request. The company registration number is required.");

        final Company otherCompany = companyDao.findByRegistrationNo(company.getRegistrationNo());
        if ((otherCompany != null && company.getPrimaryId() == null)
                || (otherCompany != null && !otherCompany.getPrimaryId().equals(company.getPrimaryId()))) {
            throw new DataIntegrityViolationException(String.format("System cannot complete request. Another company with the " +
                    "company registration number %s already exists.", company.getRegistrationNo()));
        }
    }
}
