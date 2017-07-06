package ecoo.data.upload;

import ecoo.data.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "upload_type")
public class UploadType extends BaseModel<String> {

    private static final long serialVersionUID = 1809987591875854813L;

    public enum Type {
        COMMERCIAL_INVOICE("CI"
                , "commercial_invoice.xml"
                , "dbo.upload_csv_commercial_invoice"
                , "dbo.upload_parse_commercial_invoice"
                , "dbo.upload_commercial_invoice_data"
                , new CommercialInvoiceUploadDataRowMapper()
                , CommercialInvoiceUploadData.class
                , "commercial_invoice.csv"
                , "d.product_code, d.marks"
                , "Commercial Invoice");

        private String primaryId;
        private String xmlFormatFileName;
        private String uploadStoredProcName;
        private String tableName;
        private Class<?> dataClass;
        private UploadDataRowMapper rowMapper;
        private String defaultImportFileName;
        private String orderBy;
        private String description;
        private String parseStoredProcName;

        Type(String primaryId, String xmlFormatFileName, String uploadStoredProcName, String parseStoredProcName, String tableName
                , UploadDataRowMapper rowMapper, Class<?> dataClass, String defaultImportFileName,
             String orderBy, String description) {
            this.primaryId = primaryId;
            this.xmlFormatFileName = xmlFormatFileName;
            this.uploadStoredProcName = uploadStoredProcName;
            this.parseStoredProcName = parseStoredProcName;
            this.tableName = tableName;
            this.rowMapper = rowMapper;
            this.dataClass = dataClass;
            this.defaultImportFileName = defaultImportFileName;
            this.orderBy = orderBy;
            this.description = description;
        }

        public String getPrimaryId() {
            return primaryId;
        }

        public String getXmlFormatFileName() {
            return xmlFormatFileName;
        }

        public String getUploadStoredProcName() {
            return uploadStoredProcName;
        }

        public String getParseStoredProcName() {
            return parseStoredProcName;
        }

        public String getTableName() {
            return tableName;
        }

        public UploadDataRowMapper getRowMapper() {
            return rowMapper;
        }

        public Class<?> getDataClass() {
            return dataClass;
        }

        public String getDefaultImportFileName() {
            return defaultImportFileName;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public String getDescription() {
            return description;
        }

        public static Type getTypeByPrimaryId(String id) {
            for (Type type : Type.values()) {
                if (type.getPrimaryId().equalsIgnoreCase(id)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant " + Type.class.getCanonicalName() + "." + id);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String primaryId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_upload_date")
    private Date lastUploadDate;

    /**
     * Constructs a new {@link UploadType} object.
     */
    public UploadType() {
    }

    /**
     * Constructs a new {@link UploadType} object for the given upload type.
     *
     * @param type the upload type enum
     */
    public UploadType(Type type) {
        this.primaryId = type.getPrimaryId();
        this.name = type.name().toUpperCase();
    }

    /**
     * Returns the {@link Type} enum representation of this upload type.
     *
     * @return status enum
     */
    public Type getEnum() {
        return Type.getTypeByPrimaryId(getPrimaryId());
    }

    /**
     * Returns the {@link Boolean} representation if this upload type is the given type.
     *
     * @param type the upload type enum
     * @return true if equal
     */
    public boolean isType(Type type) {
        return (getPrimaryId() != null && getPrimaryId().equals(type.getPrimaryId()));
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public String getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastUploadDate
     */
    public Date getLastUploadDate() {
        return lastUploadDate;
    }

    /**
     * @param lastUploadDate the lastUploadDate to set
     */
    public void setLastUploadDate(Date lastUploadDate) {
        this.lastUploadDate = lastUploadDate;
    }
}