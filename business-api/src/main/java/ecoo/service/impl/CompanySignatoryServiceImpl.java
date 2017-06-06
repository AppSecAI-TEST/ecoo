package ecoo.service.impl;

import ecoo.dao.CompanySignatoryDao;
import ecoo.data.CompanySignatory;
import ecoo.data.User;
import ecoo.service.CompanySignatoryService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Service
public class CompanySignatoryServiceImpl extends AuditTemplate<Integer, CompanySignatory, CompanySignatoryDao>
        implements CompanySignatoryService {

    private CompanySignatoryDao companySignatoryDao;

    @Autowired
    public CompanySignatoryServiceImpl(CompanySignatoryDao companySignatoryDao) {
        super(companySignatoryDao);
        this.companySignatoryDao = companySignatoryDao;
    }


    /**
     * Adds a signatory to the given company.
     *
     * @param user      The user to add.
     * @param companyId The company to add the signatory to.
     * @return The new signatory.
     */
    @Transactional
    @Override
    public CompanySignatory addSignatory(User user, Integer companyId) {
        Assert.notNull(user, "The variable user cannot be null.");
        Assert.notNull(companyId, "The variable companyId cannot be null.");

        CompanySignatory companySignatory = findByUserAndCompany(user.getPrimaryId(), companyId);
        if (companySignatory != null) {
            throw new DataIntegrityViolationException(String.format("System cannot complete request. A signatory for user %s (%s)" +
                    "already associated to company %s.", user.getDisplayName(), user.getPrimaryId(), companyId));
        }

        companySignatory = new CompanySignatory();
        companySignatory.setUser(user);
        companySignatory.setCompanyId(companyId);
        companySignatory.setStartDate(new Date());
        companySignatory.setEndDate(DateTime.parse("99991231", DateTimeFormat.forPattern("yyyyMMdd")).toDate());

        return save(companySignatory);
    }

    /**
     * Ends a signatory to the given company.
     *
     * @param user      The user to add.
     * @param companyId The company to add the signatory to.
     * @return The new signatory.
     */
    @Transactional
    @Override
    public CompanySignatory endSignatory(User user, Integer companyId) {
        Assert.notNull(user, "The variable user cannot be null.");
        Assert.notNull(companyId, "The variable companyId cannot be null.");

        final CompanySignatory companySignatory = findByUserAndCompany(user.getPrimaryId(), companyId);
        if (companySignatory == null) {
            throw new DataIntegrityViolationException(String.format("System cannot complete request. No signatory found " +
                    "for user %s (%s) and company %s.", user.getDisplayName(), user.getPrimaryId(), companyId));
        }

        companySignatory.setEndDate(new Date());
        return save(companySignatory);
    }

    /**
     * Returns all the signatories for the given company.
     *
     * @param companyId The company to evaluate.
     * @return The list of signatories.
     */
    @Override
    public List<CompanySignatory> findByCompanyId(Integer companyId) {
        return companySignatoryDao.findByCompanyId(companyId);
    }

    /**
     * Returns the signatory for the given user and company.
     *
     * @param userId    The user to evaluate.
     * @param companyId The company to evaluate.
     * @return The signatory or null.
     */
    @Override
    public CompanySignatory findByUserAndCompany(Integer userId, Integer companyId) {
        return companySignatoryDao.findByUserAndCompany(userId, companyId);
    }
}
