package ecoo.data;

import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "doc_coo")
@Document(type = "ecoo", indexName = "ecoo.coo", shards = 1, replicas = 0)
public class CertificateOfOrigin extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 4991785189692239969L;
    
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "shipment_id")
    @Audited
    private Integer shipmentId;

    @Column(name = "marks")
    @Audited
    private String marks;

    @Column(name = "box_no")
    @Audited
    private String boxNumber;

    @Column(name = "descr")
    @Audited
    private String descr;

    @Column(name = "tariff_code")
    @Audited
    private String tariffCode;

    @Column(name = "gross_weight")
    @Audited
    private BigDecimal grossWeight;

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

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Override
    public String toString() {
        return "CertificateOfOrigin{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", marks='" + marks + '\'' +
                ", boxNumber='" + boxNumber + '\'' +
                ", descr='" + descr + '\'' +
                ", tariffCode='" + tariffCode + '\'' +
                ", grossWeight=" + grossWeight +
                '}';
    }
}
