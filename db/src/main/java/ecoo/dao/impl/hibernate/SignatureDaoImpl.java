package ecoo.dao.impl.hibernate;

import ecoo.dao.SignatureDao;
import ecoo.data.Signature;
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
@Repository(value = "signatureDao")
public class SignatureDaoImpl extends BaseAuditLogDaoImpl<Integer, Signature> implements SignatureDao {

    @Autowired
    public SignatureDaoImpl(@Qualifier("ecooSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, Signature.class);
    }

    /**
     * Returns the signature for the given personal reference.
     *
     * @param personalReference The personal reference value like SA ID number or passport number.
     * @return The signature or null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Signature findByPersonalReference(String personalReference) {
        Assert.hasText(personalReference, "The variable personalReference cannot be null or blank.");
        final List<Signature> data = (List<Signature>) getHibernateTemplate().findByNamedQueryAndNamedParam(
                "FIND_SIGNATURE_BY_PERSONAL_REF", "personalReference", personalReference);
        if (data.isEmpty()) return null;
        return data.iterator().next();
    }
}
