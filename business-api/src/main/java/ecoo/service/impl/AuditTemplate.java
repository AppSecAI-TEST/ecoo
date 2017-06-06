package ecoo.service.impl;

import ecoo.dao.AuditLogDao;
import ecoo.dao.UserDao;
import ecoo.data.BaseModel;
import ecoo.data.audit.Revision;
import ecoo.data.KnownUser;
import ecoo.data.User;
import ecoo.service.AuditedModelAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The audit service representation of a service that provides basic auditing methods.
 *
 * @param <M> The audit model type.
 * @param <D> The dao type.
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class AuditTemplate<P, M extends BaseModel<P>, D extends AuditLogDao<P, M>> extends JdbcTemplateService<P, M>
        implements AuditedModelAware<M> {

    private D dao;

    @Autowired
    private UserDao userDao;

    /**
     * Constructs a new {@link AuditTemplate} service object.
     *
     * @param dao The object used to save/load the models.
     * @throws IllegalArgumentException If the dao is null.
     */
    public AuditTemplate(D dao) {
        super(dao);
        this.dao = dao;
    }

    /**
     * Returns the createed {@link Revision} for the given model. If no created by revision is found
     * then a default revision is found.
     *
     * @param model The model to evaluate.
     * @return A revision or null.
     */
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public Revision findCreatedBy(M model) {
        if (model == null || model.isNew()) {
            return null;
        }
        Revision revision = dao.findCreatedBy(model);

        // For legacy data provide a default revision.
        if (revision == null) {
            User systemAccount = userDao.findByPrimaryId(KnownUser.Anonymous.getPrimaryId());

            revision = new Revision();
            revision.setModifiedBy(systemAccount);
            revision.setDateModified(new Date(new GregorianCalendar(2000, Calendar.JANUARY, 1)
                    .getTimeInMillis()));
        }
        return revision;
    }

    /**
     * Returns the latest modified {@link Revision} for the given model. If no modified revision
     * found the created by revision is returned.
     *
     * @param model The model to evaluate.
     * @return A model.
     */
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public Revision findModifiedBy(M model) {
        if (model == null || model.isNew()) {
            return null;
        }
        Revision revision = dao.findModifiedBy(model);
        if (revision == null) {
            revision = findCreatedBy(model);
        }
        return revision;
    }

    /**
     * Returns the most recent revision for the given model.
     *
     * @param model The model to evaluate.
     * @return The most recent model or null if none found.
     */
    @SuppressWarnings("unchecked")
    @Override
    public M findMostRecentRevision(M model) {
        return dao.findMostRecentRevision(model);
    }
}
