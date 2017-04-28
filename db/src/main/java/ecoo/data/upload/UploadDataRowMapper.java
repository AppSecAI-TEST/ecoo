package ecoo.data.upload;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;


public abstract class UploadDataRowMapper implements RowMapper<UploadData> {

    private JdbcTemplate jdbcTemplate = null;

    public UploadDataRowMapper() {
    }

    public final void setSimpleJdbcOperations(JdbcTemplate jdbcTemplate) {
        Assert.notNull(jdbcTemplate);
        this.jdbcTemplate = jdbcTemplate;
    }

    protected final JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
