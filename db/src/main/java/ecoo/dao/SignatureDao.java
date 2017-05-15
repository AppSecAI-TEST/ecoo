package ecoo.dao;

import ecoo.data.Signature;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public interface SignatureDao extends AuditLogDao<Integer, Signature> {

    /**
     * Returns the signature for the given personal reference.
     *
     * @param personalReference The personal reference value like SA ID number or passport number.
     * @return The signature or null.
     */
    Signature findByPersonalReference(String personalReference);
}
