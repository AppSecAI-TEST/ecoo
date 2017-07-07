package ecoo.service.impl;

import ecoo.dao.SignatureDao;
import ecoo.data.Signature;
import ecoo.service.SignatureService;
import org.apache.commons.lang3.StringUtils;
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
        entity.setPersonalRefValue(clean(entity.getPersonalRefValue()));
        entity.setFirstName(clean(entity.getFirstName()));
        entity.setLastName(clean(entity.getLastName()));
        entity.setCompanyName(clean(entity.getCompanyName()));

        if (entity.isNew()) {
            entity.setDateCreated(new Date());
            entity.setUserSignatureId(null);
        }
    }

    private String clean(String word) {
        word = StringUtils.trimToNull(word);
        return word == null ? null : StringUtils.trim(word).toUpperCase();
    }
}
