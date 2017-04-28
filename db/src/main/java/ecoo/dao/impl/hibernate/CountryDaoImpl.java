package ecoo.dao.impl.hibernate;

import ecoo.dao.CountryDao;
import ecoo.data.Country;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "countryDao")
public class CountryDaoImpl extends BaseHibernateDaoImpl<String, Country> implements CountryDao {

    @Autowired
    public CountryDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Country.class);
    }
}
