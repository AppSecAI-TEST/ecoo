package ecoo.dao.impl.hibernate;

import ecoo.dao.CompanyTypeDao;
import ecoo.data.CompanyType;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Repository(value = "companyTypeDao")
public class CompanyTypeDaoImpl extends BaseAuditLogDaoImpl<String, CompanyType> implements CompanyTypeDao {

    @Autowired
    public CompanyTypeDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CompanyType.class);
    }
}
