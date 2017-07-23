package ecoo.dao;

import ecoo.data.BaseModel;
import ecoo.data.audit.Revision;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface AuditLogDao<P extends Serializable, M extends BaseModel<P>> extends BaseDao<P, M> {

    /**
     * Returns the history of the given model.
     *
     * @param id The pk of the audited entity.
     * @return A list of audited history.s
     */
    Map<Revision, M> findHistory(P id);

    /**
     * Returns the createed {@link Revision} for the given model.
     *
     * @param model the model to evaluate
     * @return the revision or null
     */
    Revision findCreatedBy(M model);

    /**
     * Returns the latest modified {@link Revision} for the given model.
     *
     * @param model the model to evaluate
     * @return the revision or null
     */
    Revision findModifiedBy(M model);

    /**
     * Returns the most recent revision for the given model.
     *
     * @param model the model to evaluate
     * @return the most recent model or null if none found
     */
    M findMostRecentRevision(M model);
}
