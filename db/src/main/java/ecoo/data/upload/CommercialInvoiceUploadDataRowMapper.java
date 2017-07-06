package ecoo.data.upload;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("SqlNoDataSourceInspection")
public final class CommercialInvoiceUploadDataRowMapper extends UploadDataRowMapper {

    @SuppressWarnings("SqlDialectInspection")
    @Override
    public final UploadData mapRow(ResultSet rs, int rowNum) throws SQLException {
        final CommercialInvoiceUploadData data = new CommercialInvoiceUploadData();

        // Common fields.
        data.setPrimaryId(rs.getInt("id"));
        data.setUploadId(rs.getInt("upload_id"));
        data.setStatus(rs.getInt("status"));
        data.setComments(rs.getString("comments"));

        // Specific fields.
        data.setMarks(rs.getString("marks"));
        data.setProductCode(rs.getString("product_code"));
        data.setDescription(rs.getString("descr"));
        data.setQuantity(rs.getString("qty"));
        data.setPrice(rs.getString("price"));
        data.setAmount(rs.getString("amount"));

        return data;
    }
}
