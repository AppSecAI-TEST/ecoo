package ecoo.dao.impl.hibernate;

import ecoo.dao.CertificateOfOriginDao;
import ecoo.data.CertificateOfOrigin;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "certificateOfOriginDao")
public class CertificateOfOriginDaoImpl extends BaseAuditLogDaoImpl<Integer, CertificateOfOrigin> implements CertificateOfOriginDao {

    @Autowired
    public CertificateOfOriginDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CertificateOfOrigin.class);
    }

    @Override
    protected boolean afterSave(CertificateOfOrigin model) {
        model.getLines().stream().filter(line -> line.getParentId() == null).forEach(line -> {
            line.setParentId(model.getPrimaryId());
            getHibernateTemplate().merge(line);
        });
        return true;
    }
}
