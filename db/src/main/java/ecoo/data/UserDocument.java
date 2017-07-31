package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "user_doc")
public class UserDocument extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -4107655202382966114L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "user_id")
    @Audited
    private Integer userId;

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

    public UserDocument() {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @JsonGetter
    public boolean isActive() {
        final DateTime now = DateTime.now();
        return now.isAfter(DateTime.now().withMillis(startDate.getTime()).withTimeAtStartOfDay())
                || now.isBefore(DateTime.now().withMillis(startDate.getTime()).withTimeAtStartOfDay());
    }

    @Override
    public String toString() {
        return "UserDocument{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
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