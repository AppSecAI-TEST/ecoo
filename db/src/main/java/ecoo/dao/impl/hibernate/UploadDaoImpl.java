package ecoo.dao.impl.hibernate;

import ecoo.dao.UploadDao;
import ecoo.data.upload.Upload;
import ecoo.data.upload.UploadStatus;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings({"unused", "Duplicates"})
@Repository(value = "importDao")
public class UploadDaoImpl extends BaseHibernateDaoImpl<Integer, Upload> implements UploadDao {

    /**
     * The {@link StoredProcedure} representation of the stored proc used to run the data import.
     *
     * @author Justin Rundle
     */
    private final static class UploadUploadStoredProc extends StoredProcedure {

        private static final String PARAM_IN_IMPORTFILE = "bulkFile";
        private static final String PARAM_IN_PARSEFILE = "parseFile";
        private static final String PARAM_IN_UPLOADID = "uploadId";

        private UploadUploadStoredProc(SessionFactory sessionFactory, String name) {
            super(SessionFactoryUtils.getDataSource(sessionFactory), name);
            declareParameter(new SqlParameter(PARAM_IN_IMPORTFILE, Types.VARCHAR));
            declareParameter(new SqlParameter(PARAM_IN_PARSEFILE, Types.VARCHAR));
            declareParameter(new SqlParameter(PARAM_IN_UPLOADID, Types.BIGINT));
        }
    }

    /**
     * The {@link StoredProcedure} representation used to run the parse data stored proc.
     *
     * @author Justin Rundle
     */
    private final static class UploadParseStoredProc extends StoredProcedure {

        private static final String PARAM_IN_UPLOAD_ID = "uploadId";

        private UploadParseStoredProc(SessionFactory sessionFactory, String name) {
            super(SessionFactoryUtils.getDataSource(sessionFactory), name);
            declareParameter(new SqlParameter(PARAM_IN_UPLOAD_ID, Types.BIGINT));
        }
    }

    /**
     * The {@link StoredProcedure} representation used to run the process stored proc.
     *
     * @author Justin Rundle
     */
    private final static class UploadProcessStoredProc extends StoredProcedure {

        private static final String PARAM_IN_UPLOAD_ID = "uploadId";
        private static final String PARAM_IN_CURRENT_USER = "currentUser";

        private UploadProcessStoredProc(SessionFactory sessionFactory, String name) {
            super(SessionFactoryUtils.getDataSource(sessionFactory), name);
            declareParameter(new SqlParameter(PARAM_IN_UPLOAD_ID, Types.BIGINT));
            declareParameter(new SqlParameter(PARAM_IN_CURRENT_USER, Types.SMALLINT));
        }
    }

    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@link UploadDaoImpl} data access object.
     */
    @Autowired
    public UploadDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory
            , @Qualifier("spivDataSource") DataSource dataSource) {
        super(sessionFactory, Upload.class);

        Assert.notNull(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.dao.upload.UploadDao#updateStatus(za.co.aforbes.fpc.db
     * .model.upload. UploadStatus.Status, za.co.aforbes.fpc.db.model.upload.Upload[])
     */
    @Override
    public final void updateStatus(UploadStatus.Status status, Upload... uploads) {
        if (status == null || uploads == null || uploads.length == 0) {
            return;
        }
        StringBuilder sql = new StringBuilder(100);
        sql.append("update ").append(Upload.class.getSimpleName()).append(" upload");
        sql.append(" set upload.status = ?");
        sql.append(" where upload.primaryId in (");

        final Collection<Object> params = new LinkedList<>();
        params.add(status.getPrimaryId());
        for (int i = 0; i < uploads.length; i++) {
            params.add(uploads[i].getPrimaryId());

            sql.append("?");
            if ((i + 1) < uploads.length) {
                sql.append(",");
            }
        }
        sql.append(")");

        getHibernateTemplate().bulkUpdate(sql.toString(), params.toArray());
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.dao.upload.UploadDao#runFileUpload(za.co.aforbes.fpc.db
     * .model.upload .Upload, java.io.File)
     */
    @Override
    public final void runFileUpload(Upload bulkUpload, String pathToCsvFile, String pathToXmlFormatFile) {
        Assert.notNull(bulkUpload);
        Assert.notNull(bulkUpload.getUploadType());
        Assert.notNull(pathToCsvFile);
        Assert.notNull(pathToXmlFormatFile);

        Map<String, Object> params = new LinkedHashMap<>();
        params.put(UploadUploadStoredProc.PARAM_IN_IMPORTFILE, pathToCsvFile);
        params.put(UploadUploadStoredProc.PARAM_IN_PARSEFILE, pathToXmlFormatFile);
        params.put(UploadUploadStoredProc.PARAM_IN_UPLOADID, bulkUpload.getPrimaryId());

        StoredProcedure importStoredProc = new UploadUploadStoredProc(getSessionFactory(), bulkUpload
                .getUploadType().getUploadStoredProcName());
        importStoredProc.execute(params);
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.dao.upload.UploadDao#parseUpload(za.co.aforbes.fpc.db.
     * model.upload.Upload )
     */
    @Override
    public final void parse(Upload bulkUpload) {
        Assert.notNull(bulkUpload);
        Assert.notNull(bulkUpload.getUploadType());

        Map<String, Object> params = new LinkedHashMap<>();
        params.put(UploadParseStoredProc.PARAM_IN_UPLOAD_ID, bulkUpload.getPrimaryId());

        StoredProcedure parseStoredProc = new UploadParseStoredProc(getSessionFactory(), bulkUpload
                .getUploadType().getParseStoredProcName());
        parseStoredProc.execute(params);
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.dao.upload.UploadDao#findUploadByStatus(za.co.aforbes.fpc.common
     * .db.model.upload .UploadStatus.Status[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public final List<Upload> findUploadsByStatus(UploadStatus.Status... status) {
        if (status == null || status.length == 0) {
            return new LinkedList<>();
        }

        StringBuilder sql = new StringBuilder(100);
        sql.append("select distinct upload");
        sql.append(" from ").append(Upload.class.getSimpleName()).append(" upload");
        sql.append(" where upload.status in (");

        Collection<Object> params = new LinkedList<>();
        for (int i = 0; i < status.length; i++) {
            params.add(status[i].getPrimaryId());

            sql.append("?");
            if ((i + 1) < status.length) {
                sql.append(",");
            }
        }
        sql.append(")");

        return (List<Upload>) getHibernateTemplate().find(sql.toString(), params.toArray());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * za.co.aforbes.fpc.db.dao.bulkimport.UploadDao#countBatchByStatus(za.co.aforbes.fpc.db.model.bulkimport
     * .UploadStatus.Status[])
     */
    @Override
    public final int countByStatus(UploadStatus.Status... status) {
        Assert.notNull(status);
        Assert.notEmpty(status);
        Assert.noNullElements(status);

        StringBuilder sql = new StringBuilder(100);
        Collection<Object> params = new LinkedList<>();

        sql.append("select count(*)");
        sql.append(" from dbo.upload with(nolock)");

        sql.append(" where status in(");
        for (int i = 0; i < status.length; i++) {
            sql.append("?");
            params.add(status[i].getPrimaryId());
            if ((i + 1) < status.length) {
                sql.append(",");
            }
        }
        sql.append(")");

        final List<Integer> data = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> rs.getInt(""), params.toArray());
        if (data == null || data.isEmpty()) return 0;
        return data.iterator().next();
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.dao.bulkimport.UploadDao#countByMapping(java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    public final int countByMapping(Integer importId, Integer mappingId) {
        Assert.notNull(mappingId);

        StringBuilder sql = new StringBuilder(100);
        Collection<Object> params = new LinkedList<>();

        sql.append("select count(*)");
        sql.append(" from dbo.upload with(nolock)");

        sql.append(" where mappingId = ?");
        params.add(mappingId);

        if (importId != null) {
            sql.append(" and id <> ?");
            params.add(importId);
        }

        final List<Integer> data = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> rs.getInt(""), params.toArray());
        if (data == null || data.isEmpty()) return 0;
        return data.iterator().next();
    }
}
