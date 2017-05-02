package ecoo.dao.impl.hibernate;

import ecoo.dao.CompanyDao;
import ecoo.data.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Repository(value = "companyDao")
public class CompanyDaoImpl extends BaseAuditLogDaoImpl<Integer, Company> implements CompanyDao {

    @Autowired
    public CompanyDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Company.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Company findByRegistrationNo(String registrationNo) {
        Assert.notNull(registrationNo, "The variable registrationNo cannot be null.");
        final List<Company> data = (List<Company>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_COMPANY_BY_REGISTRATION_NUMBER", "registrationNo", registrationNo);
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }
}
