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
public class CommercialInvoiceAmount extends BaseModel<Integer> implements Serializable {

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

    @ManyToOne
    @JoinColumn(name = "amount_type")
    @Audited
    private AmountType amountType;

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

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}