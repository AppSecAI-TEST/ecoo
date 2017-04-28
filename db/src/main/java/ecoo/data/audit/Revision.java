package ecoo.data.audit;

import ecoo.data.BaseModel;
import ecoo.data.User;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * This class is a model of the REVINFO table created by Envers. It uses the @RevisionEntity
 * annotation provided by the Envers framework.
 *
 * @author Justin Rundle
 * @since August 2016
 */
@Entity
@RevisionEntity(RevisionAuditListener.class)
@Table(name = "revision")
public final class Revision extends BaseModel<Long> {

    @Id
    @GeneratedValue
    @RevisionNumber
    @Column(name = "id")
    private Long primaryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @RevisionTimestamp
    @Column(name = "timestamp")
    private Long timeStamp;

    @Column(name = "date_modified")
    private Date dateModified;

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#getPrimaryId()
     */
    @Override
    public final Long getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the modifiedBy
     */
    public final User getModifiedBy() {
        return modifiedBy;
    }

    /**
     * @param modifiedBy the modifiedBy to set
     */
    public final void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * @return the timeStamp
     */
    public final Long getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return the dateModified
     */
    public final Date getDateModified() {
        return dateModified;
    }

    /**
     * @param dateModified the dateModified to set
     */
    public final void setDateModified(Date dateModified) {
        if (dateModified == null) {
            this.timeStamp = null;
        } else {
            this.timeStamp = dateModified.getTime();
        }
        this.dateModified = dateModified;
    }

    /**
     * The String representation of this object.
     *
     * @return The string representation of this object.
     */
    @Override
    public final String toString() {
        return "Revision{" +
                "primaryId=" + primaryId +
                ", modifiedBy=" + modifiedBy +
                ", timeStamp=" + timeStamp +
                ", dateModified=" + dateModified +
                '}';
    }
}