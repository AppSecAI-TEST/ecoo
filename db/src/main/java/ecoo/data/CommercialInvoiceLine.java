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
@Table(name = "doc_comm_inv_ln")
public class CommercialInvoiceLine extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -5005255906788175733L;

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
        return "CommercialInvoiceLine{" +
                "primaryId=" + primaryId +
                ", parentId=" + parentId +
                ", marks='" + marks + '\'' +
                ", productCode='" + productCode + '\'' +
                ", descr='" + descr + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
