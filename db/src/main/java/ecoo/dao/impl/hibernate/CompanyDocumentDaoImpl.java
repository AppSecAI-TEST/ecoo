package ecoo.dao.impl.hibernate;

import ecoo.dao.CompanyDocumentDao;
import ecoo.data.CompanyDocument;
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
@Repository(value = "companyDocumentDao")
public class CompanyDocumentDaoImpl extends BaseAuditLogDaoImpl<Integer, CompanyDocument> implements CompanyDocumentDao {

    @Autowired
    public CompanyDocumentDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CompanyDocument.class);
    }

    /**
     * Returns the list of company documents for the given company.
     *
     * @param companyId The company pk.
     * @return A list.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CompanyDocument> findByCompany(Integer companyId) {
        Assert.notNull(companyId, "The variable companyId cannot be null.");
        return (List<CompanyDocument>) getHibernateTemplate().findByNamedQueryAndNamedParam("FIND_DOCUMENTS_BY_COMPANY"
                , "companyId", companyId);
    }
}
