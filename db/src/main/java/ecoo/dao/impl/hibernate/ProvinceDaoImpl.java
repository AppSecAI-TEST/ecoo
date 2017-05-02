package ecoo.dao.impl.hibernate;

import ecoo.dao.ProvinceDao;
import ecoo.data.Province;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "provinceDao")
public class ProvinceDaoImpl extends BaseHibernateDaoImpl<Integer, Province> implements ProvinceDao {

    @Autowired
    public ProvinceDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Province.class);
    }
}
