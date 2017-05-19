package ecoo.dao.impl.hibernate;

import ecoo.dao.CompanySignatoryDao;
import ecoo.data.CompanySignatory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Repository(value = "companySignatoryDao")
public class CompanySignatoryDaoImpl extends BaseAuditLogDaoImpl<Integer, CompanySignatory> implements CompanySignatoryDao {

    @Autowired
    public CompanySignatoryDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CompanySignatory.class);
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
        Assert.notNull(userId, "The variable userId cannot be null.");
        Assert.notNull(companyId, "The variable companyId cannot be null.");
        final List<?> data = getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_COMPANY_SIGNATORY_BY_USER_AND_COMPANY"
                , new String[]{"userId", "companyId"}, new Object[]{userId, companyId});
        if (data.isEmpty()) return null;
        return (CompanySignatory) data.iterator().next();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CompanySignatory> findByCompanyId(Integer companyId) {
        Assert.notNull(companyId, "The variable companyId cannot be null.");
        return (List<CompanySignatory>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_COMPANY_SIGNATORIES_BY_COMPANY", "companyId", companyId);
    }
}
