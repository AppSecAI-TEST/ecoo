package ecoo.service;

import ecoo.data.BaseModel;
import ecoo.data.audit.Revision;

/**
 * Interface used to defined that the subclass is aware that it implements a cache of some sorts.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public interface AuditedModelAware<M extends BaseModel<?>> {

    /**
     * Returns the createed {@link Revision} for the given model. If no created by revision is found
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