package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "chamber_admin")
public class ChamberAdmin extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -1354480424844108481L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "chamber_id")
    @Audited
    private Integer chamberId;

    @Column(name = "user_id")
    @Audited
    private Integer userId;

    @Column(name = "start_date")
    @Audited
    private Date startDate;

    @Column(name = "end_date")
    @Audited
    private Date endDate;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    public boolean isActive(DateTime effectiveDate) {
        final DateTime startDate = DateTime.now().withMillis(this.startDate.getTime())
                .withTimeAtStartOfDay();
        final DateTime endDate = DateTime.now().withMillis(this.endDate.getTime());
        return (effectiveDate.isEqual(startDate) || effectiveDate.isAfter(startDate))
                && effectiveDate.isBefore(endDate);
    }

    @Override
    public String toString() {
        return "ChamberAdmin{" +
                "primaryId=" + primaryId +
                ", chamberId=" + chamberId +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}