package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "company_doc")
public class CompanyDocument extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 8647765913897173175L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "company_id")
    @Audited
    private Integer companyId;

    @Column(name = "doc_type")
    @Audited
    private String documentType;

    @Column(name = "file_name")
    @Audited
    private String fileName;

    @Column(name = "encoded_image")
    @Audited
    private String encodedImage;

    @Column(name = "mime_type")
    @Audited
    private String mimeType;

    @Column(name = "size_in_kb")
    @Audited
    private long sizeInKb;

    @Column(name = "date_created")
    @Audited
    private Date dateCreated;


    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSizeInKb() {
        return sizeInKb;
    }

    public void setSizeInKb(long sizeInKb) {
        this.sizeInKb = sizeInKb;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "CompanyDocument{" +
                "primaryId=" + primaryId +
                ", companyId=" + companyId +
                ", documentType='" + documentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", encodedImage='" + encodedImage + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInKb=" + sizeInKb +
                ", dateCreated=" + dateCreated +
                '}';
    }
}