package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "chamber_doc")
public class ChamberDocument extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -2065816721861863179L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "chamber_id")
    @Audited
    private Integer chamberId;

    @Column(name = "doc_type")
    @Audited
    private String documentType;

    @Column(name = "file_name")
    @Audited
    private String fileName;

    @Column(name = "encoded_image")
    private String encodedImage;

    @Column(name = "mime_type")
    @Audited
    private String mimeType;

    @Column(name = "size_in_kb")
    @Audited
    private long sizeInKb;

    @Column(name = "start_date")
    @Audited
    private Date startDate;

    @Column(name = "end_date")
    @Audited
    private Date endDate;


    public ChamberDocument() {
    }

    @JsonIgnore
    public boolean isDocumentType(DocumentTypes documentType) {
        return this.documentType != null && this.documentType.equalsIgnoreCase(documentType.getPrimaryId());
    }


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

    public Integer getChamberId() {
        return chamberId;
    }

    public void setChamberId(Integer chamberId) {
        this.chamberId = chamberId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ChamberDocument{" +
                "primaryId=" + primaryId +
                ", chamberId=" + chamberId +
                ", documentType='" + documentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", encodedImage='" + encodedImage + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInKb=" + sizeInKb +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}