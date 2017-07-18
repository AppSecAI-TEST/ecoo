package ecoo.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class ChamberUserListRow implements Serializable {

    private static final long serialVersionUID = 7976453596231410768L;

    private Integer primaryId;

    private Integer userId;

    private String displayName;

    private String username;

    private String personalReferenceValue;

    private String primaryEmailAddress;

    private String companyName;

    private String designation;

    private String status;

    private boolean member;

    private Date startDate;

    private Date endDate;

    private boolean active;

    public ChamberUserListRow(Integer primaryId, Integer userId, String displayName, String username
            , String personalReferenceValue, String primaryEmailAddress, String companyName, String designation
            , String status, boolean member, Date startDate, Date endDate, boolean active) {
        this.primaryId = primaryId;
        this.userId = userId;
        this.displayName = displayName;
        this.username = username;
        this.personalReferenceValue = personalReferenceValue;
        this.primaryEmailAddress = primaryEmailAddress;
        this.companyName = companyName;
        this.designation = designation;
        this.status = status;
        this.member = member;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonalReferenceValue() {
        return personalReferenceValue;
    }

    public String getPrimaryEmailAddress() {
        return primaryEmailAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getStatus() {
        return status;
    }

    public boolean isMember() {
        return member;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }
}