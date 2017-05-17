package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Entity
@Table(name = "company_sign")
public class CompanySignatory extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = -1354480424844108481L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "company_id")
    @Audited
    private Integer companyId;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    @Override
    public String toString() {
        return "CompanySignatory{" +
                "primaryId=" + primaryId +
                ", companyId=" + companyId +
                ", userId=" + userId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}