package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Entity
@Table(name = "chamber_rate")
public class ChamberRate extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -4273381441314194973L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "chamber_id")
    @Audited
    private Integer chamberId;

    @Column(name = "member")
    @Audited
    private boolean member;

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

    public Integer getChamberId() {
        return chamberId;
    }

    public void setChamberId(Integer chamberId) {
        this.chamberId = chamberId;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ChamberRate{" +
                "primaryId=" + primaryId +
                ", chamberId=" + chamberId +
                ", member=" + member +
                ", amount=" + amount +
                '}';
    }
}