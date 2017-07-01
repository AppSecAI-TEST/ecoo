package ecoo.dao.impl.hibernate;

import ecoo.dao.CertificateOfOriginLineDao;
import ecoo.data.CertificateOfOriginLine;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@SuppressWarnings("unused")
@Repository(value = "certificateOfOriginLineDao")
public class CertificateOfOriginLineDaoImpl extends BaseAuditLogDaoImpl<Integer, CertificateOfOriginLine>
        implements CertificateOfOriginLineDao {

    @Autowired
    public CertificateOfOriginLineDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, CertificateOfOriginLine.class);
    }
}
