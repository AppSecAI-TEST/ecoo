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
@Table(name = "doc_pack_list")
@Document(type = "ecoo.packing.list", indexName = "ecoo.packing.list", shards = 1, replicas = 0)
public class PackingList extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 3990141658386743293L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "shipment_id")
    @Audited
    private Integer shipmentId;

    @Column(name = "product_code")
    @Audited
    private String productCode;

    @Column(name = "descr")
    @Audited
    private String descr;

    @Column(name = "qty")
    @Audited
    private BigDecimal qty;

    @Column(name = "net_weight")
    @Audited
    private BigDecimal netWeight;

    @Column(name = "gross_weight")
    @Audited
    private BigDecimal grossWeight;

    @Column(name = "volume")
    @Audited
    private BigDecimal volume;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public BigDecimal getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "PackingList{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", productCode='" + productCode + '\'' +
                ", descr='" + descr + '\'' +
                ", qty=" + qty +
                ", netWeight=" + netWeight +
                ", grossWeight=" + grossWeight +
                ", volume=" + volume +
                '}';
    }
}