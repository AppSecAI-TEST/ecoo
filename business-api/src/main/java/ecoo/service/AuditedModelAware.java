package ecoo.service;

import ecoo.data.BaseModel;
import ecoo.data.audit.Revision;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface used to defined that the subclass is aware that it implements a cache of some sorts.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public interface AuditedModelAware<P extends Serializable, M extends BaseModel<P>> {

    /**
     * Returns the history of the given model.
     *
     * @param id The pk of the audited entity.
     * @return A list of audited history.s
     */
    Map<Revision, M> findHistory(P id);

    /**
     * Returns the created {@link Revision} for the given model. If no created by revision is found
     * then a default revision is found.
     *
     * @param model The model to evaluate.
     * @return A revision or null.
     */
    Revision findCreatedBy(M model);

    /**
     * Returns the latest modified {@link Revision} for the given model. If no modified revision
     * found the created by revision is returned.
     *
     * @param model The model to evaluate.
     * @return A model.
     */
    Revision findModifiedBy(M model);

    /**
     * Returns the most recent revision for the given model.
     *
     * @param model The model to evaluate.
     * @return The most recent model or null if none found.
     */
    M findMostRecentRevision(M model);
}