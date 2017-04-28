package ecoo.dao.impl.hibernate;

import ecoo.dao.UploadDataDao;
import ecoo.data.upload.Upload;
import ecoo.data.upload.UploadData;
import ecoo.data.upload.UploadDataRowMapper;
import ecoo.data.upload.UploadStatus;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unchecked")
@Repository(value = "uploadDataDao")
public class UploadDataDaoImpl extends BaseHibernateDaoImpl<Integer, UploadData> implements UploadDataDao {

    private static final double MAX_NUM_PARAMETERS = 1500;

    private static final Logger log = LoggerFactory.getLogger(UploadDataDaoImpl.class.getSimpleName());

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@link UploadDataDaoImpl} data access object.
     */
    @Autowired
    public UploadDataDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory
            , @Qualifier("spivDataSource") DataSource dataSource) {
        super(sessionFactory, UploadData.class);

        Assert.notNull(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Returns the import data object for the given primary id.
     *
     * @param primaryId The primary id of the data record.
     * @param clazz     The sub data class.
     * @return The data object or null if none found.
     */
    @Override
    public <D extends UploadData> D findDataByPrimaryId(Integer primaryId, Class<D> clazz) {
        Assert.notNull(primaryId);
        Assert.notNull(clazz);

        String sql = "select data" +
                " from " + clazz.getSimpleName() + " data" +
                " where data.primaryId = ?";

        final Collection<D> data = (Collection<D>) getHibernateTemplate().find(sql, primaryId);
        if (data.isEmpty()) {
            return null;
        }
        return data.iterator().next();
    }

    /**
     * Method used to bulk update the status for a collection of data records.
     *
     * @param anUpload  the owner of the data
     * @param data      the import data
     * @param newStatus the new status
     */
    @Override
    public void bulkUpdate(Upload anUpload, Collection<UploadData> data, UploadStatus.Status newStatus) {
        Assert.notNull(anUpload);
        Assert.notNull(data);
        Assert.notNull(newStatus);

        if (data.size() < MAX_NUM_PARAMETERS) {
            Collection<Object> params = new LinkedList<>();
            StringBuilder sql = new StringBuilder(255);
            sql.append("update ").append(ClassUtils.getShortName(anUpload.getUploadType().getDataClass())).append(" data");

            sql.append(" set data.status = ?");
            params.add(newStatus.getPrimaryId());

            sql.append(" where data.uploadId = ?");
            params.add(anUpload.getPrimaryId());

            sql.append(" and data.primaryId in (");
            for (Iterator<UploadData> i = data.iterator(); i.hasNext(); ) {
                sql.append("?");
                params.add(i.next().getPrimaryId());

                if (i.hasNext()) {
                    sql.append(",");
                }
            }
            sql.append(")");
            getHibernateTemplate().bulkUpdate(sql.toString(), params.toArray());

        } else {
            final List<UploadData> copy = new LinkedList<>(data);

            int fromIndex = 0;
            int toIndex = (int) MAX_NUM_PARAMETERS;

            for (int i = 0; i < Math.ceil((copy.size() / MAX_NUM_PARAMETERS)); i++) {
                Collection<Object> params = new LinkedList<>();
                StringBuilder sql = new StringBuilder(1024);

                sql.append("update ").append(ClassUtils.getShortName(anUpload.getUploadType().getDataClass())).append(" data");

                sql.append(" set data.status = ?");
                params.add(newStatus.getPrimaryId());

                sql.append(" where data.uploadId = ?");
                params.add(anUpload.getPrimaryId());

                sql.append(" and data.primaryId in (");

                for (int j = fromIndex; j < toIndex; j++) {
                    sql.append("?");
                    params.add(copy.get(j).getPrimaryId());

                    if ((j + 1) < toIndex) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                getHibernateTemplate().bulkUpdate(sql.toString(), params.toArray());

                fromIndex = toIndex;
                toIndex = fromIndex + (int) MAX_NUM_PARAMETERS;
                if (toIndex > copy.size()) {
                    toIndex = copy.size();
                }
            }
        }
    }

    /**
     * Method used to bulk update the upload data for a specific upload.
     *
     * @param anUpload  the owner of the data
     * @param oldStatus the previous status
     * @param newStatus the new status to change to
     */
    @Override
    public void bulkUpdateStatus(Upload anUpload, UploadStatus.Status oldStatus, UploadStatus.Status newStatus) {
        Assert.notNull(anUpload);
        Assert.notNull(oldStatus);
        Assert.notNull(newStatus);

        String sql = ("update " + ClassUtils.getShortName(anUpload.getUploadType().getDataClass()) + " data") +
                " set data.status = ?" +
                " where data.uploadId = ?" +
                " and data.status = ?";

        getHibernateTemplate().bulkUpdate(sql, newStatus.getPrimaryId(), anUpload.getPrimaryId(), oldStatus.getPrimaryId());
    }

    /**
     * Returns a {@link Collection} of import data of the given search critera.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @param start    The start row index.
     * @param end      The end row index.
     * @return A collection of import data.
     */
    @Override
    public Collection<UploadData> findUploadDataByStatus(Upload anUpload, UploadStatus.Status status, int start, int end) {
        Assert.notNull(anUpload);

        Collection<Object> params = new LinkedList<>();
        StringBuilder sql = new StringBuilder(255);

        sql.append("select importdata.* from (select row_number() over (order by ").append(anUpload.getUploadType().getOrderBy()).append(")");
        sql.append(" as rownum, d.* from ").append(anUpload.getUploadType().getTableName()).append(" d with (nolock)");
        sql.append(" where d.upload_id = ?");
        params.add(anUpload.getPrimaryId());

        if (status != null) {
            sql.append(" and d.status = ?");
            params.add(status.getPrimaryId());
        }

        sql.append(") as importdata where importdata.rownum between (?) and (?)");
        params.add(start);
        params.add(end);

        final UploadDataRowMapper rowMapper = anUpload.getUploadType().getRowMapper();
        rowMapper.setSimpleJdbcOperations(jdbcTemplate);

        final Collection<UploadData> data = jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
        log.debug(String.format("Found %s record(s) between %s and %s.", data.size(), start, end));
        return data;
    }

    /**
     * Returns a {@link Collection} of import data of the given search critera.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @return A collection of import data.
     */
    @Override
    public Collection<UploadData> findUploadDataByStatus(Upload anUpload, UploadStatus.Status... status) {
        Assert.notNull(anUpload);

        Collection<Object> params = new LinkedList<>();
        StringBuilder sql = new StringBuilder(255);

        sql.append("select d from ").append(ClassUtils.getShortName(anUpload.getUploadType().getDataClass())).append(" d");
        sql.append(" where d.uploadId = ?");
        params.add(anUpload.getPrimaryId());

        buildStatusCriteria(params, sql, status);
        return (Collection<UploadData>) getHibernateTemplate().find(sql.toString(), params.toArray());
    }

    @SuppressWarnings("Duplicates")
    private void buildStatusCriteria(Collection<Object> params, StringBuilder sql, UploadStatus.Status[] status) {
        if (status != null && status.length > 0 && status[0] != null) {
            sql.append(" and d.status in (");
            for (int i = 0; i < status.length; i++) {
                sql.append("?");
                params.add(status[i].getPrimaryId());

                if ((i + 1) < status.length) {
                    sql.append(",");
                }
            }
            sql.append(")");
        }
    }

    /**
     * Returns a {@link Long} representation of the number of rows that are in the given status.
     *
     * @param anUpload The owner of the data.
     * @param status   The upload status.
     * @return The count.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public long countUploadDataByStatus(Upload anUpload, UploadStatus.Status... status) {
        Assert.notNull(anUpload);

        final Collection<Object> params = new LinkedList<>();
        final StringBuilder sql = new StringBuilder(255);

        sql.append("select count(*)");
        sql.append(" from ").append(anUpload.getUploadType().getTableName()).append(" d WITH (NOLOCK)");
        sql.append(" where d.upload_id = ?");
        params.add(anUpload.getPrimaryId());

        buildStatusCriteria(params, sql, status);

        final List<Long> data = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> rs.getLong("")
                , params.toArray());
        if (data == null || data.isEmpty()) return 0;
        return data.iterator().next();
    }
}
