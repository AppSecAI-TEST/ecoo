package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "shipment_doc")
public class ShipmentDocument extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 453376563925311953L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "shipment_id")
    @Audited
    private Integer shipmentId;

    @Column(name = "doc_type")
    @Audited
    private String documentType;

    @Column(name = "file_name")
    @Audited
    private String fileName;

    @Column(name = "data")
    private String data;

    @Column(name = "mime_type")
    @Audited
    private String mimeType;

    @Column(name = "size_in_kb")
    @Audited
    private long sizeInKb;

    @Column(name = "date_created")
    @Audited
    private Date dateCreated;

    public ShipmentDocument() {
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

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
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

    @JsonIgnore
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
        return "ShipmentDocument{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", documentType='" + documentType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", data='" + data + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInKb=" + sizeInKb +
                ", dateCreated=" + dateCreated +
                '}';
    }
}