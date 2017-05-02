package ecoo.dao.impl.hibernate;

import ecoo.dao.TitleDao;
import ecoo.data.Title;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "titleDao")
public class TitleDaoImpl extends BaseHibernateDaoImpl<String, Title> implements TitleDao {

    @Autowired
    public TitleDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Title.class);
    }
}
