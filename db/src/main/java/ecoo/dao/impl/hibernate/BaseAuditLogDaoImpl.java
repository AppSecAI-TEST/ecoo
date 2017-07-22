package ecoo.dao.impl.hibernate;

import ecoo.dao.AuditLogDao;
import ecoo.data.BaseModel;
import ecoo.data.audit.Revision;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The {@link BaseHibernateDaoImpl} data access object used to load/save the parametized model. This
 * data access object has further audit capabilities with the ability to be to find revisions for
 * this model.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class BaseAuditLogDaoImpl<P extends Serializable, M extends BaseModel<P>> extends
        BaseHibernateDaoImpl<P, M> implements AuditLogDao<P, M> {

    private static final int PROPERTY_REVISION_MODEL_INDEX = 0;
    private static final int PROPERTY_REVISION_INDEX = 1;
//    public static final int PROPERTY_REVISION_TYPE_INDEX = 2;

    /**
     * Constructs a new {@link BaseAuditLogDaoImpl} data access object.
     *
     * @param sessionFactory The Hibernate session factory.
     * @param baseModelClass The base class name.
     */
    protected BaseAuditLogDaoImpl(SessionFactory sessionFactory, final Class<M> baseModelClass) {
        super(sessionFactory, baseModelClass);
    }

    /**
     * Returns the history of the given model.
     *
     * @param id The pk of the audited entity.
     * @return A list of audited history.s
     */
    @Override
    public List<M> findHistory(P id) {
        final List<Object> history = new ArrayList<>();
        final Session session = getSessionFactory().getCurrentSession();
        final AuditReader reader = AuditReaderFactory.get(session);

        final List<Number> revisionIds = reader.getRevisions(getBaseModelClass(), id);
        for (final Number revisionId : revisionIds) {
            AuditQuery query = reader.createQuery().forEntitiesAtRevision(getBaseModelClass(), revisionId);
            List entity = query.getResultList();
            history.add(entity);
        }

        return null;
    }


//        query.setMaxResults(1);
//        query.add(AuditEntity.id().eq(model.getPrimaryId()));
//        query.add(AuditEntity.revisionType().eq(RevisionType.ADD));
//
//        Collection<Object[]> data = query.getResultList();
//
//        EntityManager em = entityManagerFactory.createEntityManager();
//        AuditReader reader = AuditReaderFactory.get(em);
//        List<Number> revisions = reader.getRevisions(Patient.class, patientId);
//        List<HistoryDto> historyList = findHistoryDetails(revisions);
//        List<HistoryDto> legalHistoryList = findLegalHistoryByPatient(patientId);
//        historyList.addAll(legalHistoryList);
//        Collections.sort(historyList);
//    }

    /*
         * (non-Javadoc)
         *
         * @see
         * za.co.aforbes.fpc.db.dao.audit.AuditLogDao#findCreatedBy(za.co.aforbes.fpc.
         * db.model.BaseModel )
         */
    @SuppressWarnings("unchecked")
    @Override
    public final Revision findCreatedBy(M model) {
        if (model == null || model.isNew()) {
            return null;
        }

        final Session session = getSessionFactory().getCurrentSession();
        AuditReader reader = AuditReaderFactory.get(session);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getBaseModelClass(), false, true);

        query.setMaxResults(1);
        query.add(AuditEntity.id().eq(model.getPrimaryId()));
        query.add(AuditEntity.revisionType().eq(RevisionType.ADD));

        Collection<Object[]> data = query.getResultList();
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (Revision) data.iterator().next()[PROPERTY_REVISION_INDEX];
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * za.co.aforbes.fpc.db.dao.audit.AuditLogDao#findModifiedBy(za.co.aforbes.fpc.common
     * .db.model.BaseModel )
     */
    @SuppressWarnings("unchecked")
    @Override
    public final Revision findModifiedBy(M model) {
        if (model == null || model.isNew()) {
            return null;
        }

        final Session session = getSessionFactory().getCurrentSession();
        AuditReader reader = AuditReaderFactory.get(session);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(getBaseModelClass(), false, true);

        query.setMaxResults(1);
        query.add(AuditEntity.id().eq(model.getPrimaryId()));
        query.add(AuditEntity.revisionType().eq(RevisionType.MOD));
        query.addOrder(AuditEntity.revisionProperty("dateModified").desc());

        Collection<Object[]> data = query.getResultList();
        if (data == null || data.isEmpty()) {
            return null;
        }
        return (Revision) data.iterator().next()[PROPERTY_REVISION_INDEX];
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * za.co.aforbes.fpc.db.dao.audit.AuditLogDao#findMostRecentRevision(za.co.aforbes.fpc
     * .common.db.model .BaseModel)
     */
    @SuppressWarnings("unchecked")
    public final M findMostRecentRevision(M model) {
        if (model == null || model.isNew()) {
            return model;
        }

        AuditQuery query = null;
        Session session;
        try {
            session = getSessionFactory().getCurrentSession();
            AuditReader reader = AuditReaderFactory.get(session);
            query = reader.createQuery().forRevisionsOfEntity(getBaseModelClass(), false, false);

            query.setMaxResults(1);
            query.add(AuditEntity.id().eq(model.getPrimaryId()));
            query.addOrder(AuditEntity.revisionProperty("dateModified").desc());

            Collection<Object[]> data = query.getResultList();
            if (data == null || data.isEmpty()) {
                return model;
            }
            return (M) data.iterator().next()[PROPERTY_REVISION_MODEL_INDEX];
        } finally {
            if (query != null) query.setMaxResults(0);
            // if (session != null) session.close();
        }
    }
}
