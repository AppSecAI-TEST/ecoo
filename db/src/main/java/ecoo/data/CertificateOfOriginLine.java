package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "doc_coo_ln")
public class CertificateOfOriginLine extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 7354968025491578532L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "parent_id")
    @Audited
    private Integer parentId;

    @Column(name = "marks")
    @Audited
    private String marks;

    @Column(name = "descr")
    @Audited
    private String descr;

    @Column(name = "origin")
    @Audited
    private String origin;

    @Column(name = "qty")
    @Audited
    private BigDecimal qty;

    @Column(name = "price")
    @Audited
    private BigDecimal price;

    @Column(name = "amount")
    @Audited
    private BigDecimal amount;

    @Column(name = "price_stated")
    @Audited
    private boolean priceStated;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public boolean isPriceStated() {
        return priceStated;
    }

    public void setPriceStated(boolean priceStated) {
        this.priceStated = priceStated;
    }

    @Override
    public String toString() {
        return "CertificateOfOriginLine{" +
                "primaryId=" + primaryId +
                ", parentId=" + parentId +
                ", marks='" + marks + '\'' +
                ", descr='" + descr + '\'' +
                ", origin='" + origin + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", amount=" + amount +
                ", priceStated=" + priceStated +
                '}';
    }
}
