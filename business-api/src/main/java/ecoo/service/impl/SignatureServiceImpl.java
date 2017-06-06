package ecoo.service.impl;

import ecoo.dao.SignatureDao;
import ecoo.data.Signature;
import ecoo.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Service
public class SignatureServiceImpl extends AuditTemplate<Integer, Signature, SignatureDao>
        implements SignatureService {

    private SignatureDao signatureDao;

    @Autowired
    public SignatureServiceImpl(SignatureDao signatureDao) {
        super(signatureDao);
        this.signatureDao = signatureDao;
    }

    /**
     * Returns the signature for the given personal reference.
     *
     * @param personalReference The personal reference value like SA ID number or passport number.
     * @return The signature or null.
     */
    @Override
    public Signature findByPersonalReference(String personalReference) {
        Assert.hasText(personalReference, "The variable personalReference cannot be null or blank.");
        return signatureDao.findByPersonalReference(personalReference);
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    @Override
    protected void beforeSave(Signature entity) {
        if (entity.isNew()) {
            entity.setDateCreated(new Date());
            entity.setUserSignatureId(null);
        }
    }
}
