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
@Table(name = "doc_comm_inv")
@Document(type = "ecoo", indexName = "ecoo.commercial.invoice", shards = 1, replicas = 0)
public class CommercialInvoice extends BaseModel<Integer> implements Serializable {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    @Audited
    private Integer primaryId;

    @Column(name = "product_code")
    @Audited
    private String productCode;

    @Column(name = "descr")
    @Audited
    private String descr;

    @Column(name = "qty")
    @Audited
    private BigDecimal qty;

    @Column(name = "price")
    @Audited
    private BigDecimal price;

    @Column(name = "amount")
    @Audited
    private BigDecimal amount;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CommercialInvoice{" +
                "primaryId=" + primaryId +
                ", productCode='" + productCode + '\'' +
                ", descr='" + descr + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}