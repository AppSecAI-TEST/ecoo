package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "doc_comm_inv_amount")
public class CommercialInvoiceIncotermAmount extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 615985896272545568L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "shipment_id")
    @Audited
    private Integer shipmentId;

    @Column(name = "incoterm_amount_type")
    @Audited
    private String incotermAmountTypeId;

    @Column(name = "amount")
    @Audited
    private String amount;

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

    public String getIncotermAmountTypeId() {
        return incotermAmountTypeId;
    }

    public void setIncotermAmountTypeId(String incotermAmountTypeId) {
        this.incotermAmountTypeId = incotermAmountTypeId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CommercialInvoiceIncotermAmount{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", incotermAmountTypeId='" + incotermAmountTypeId + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}