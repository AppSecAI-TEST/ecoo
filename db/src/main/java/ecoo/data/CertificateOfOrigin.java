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
@Document(type = "ecoo.coo", indexName = "ecoo.coo", shards = 1, replicas = 0)
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

    @Column(name = "descr")
    @Audited
    private String descr;

    @Column(name = "qty")
    @Audited
    private BigDecimal qty;

    @Column(name = "price")
    @Audited
    private BigDecimal price;

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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CertificateOfOrigin{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", marks='" + marks + '\'' +
                ", descr='" + descr + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
