package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.builder.TimeDescriptionBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "system_job")
public class SystemJob extends BaseModel<Integer> {

    private static final long serialVersionUID = 2406713878903695016L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "processing_time")
    private long processingTime;

    @Column(name = "successful_ind")
    private String successfulProcessingInd;

    @Column(name = "comment")
    private String comments;

    /**
     * Constructs a new {@link SystemJob} model object.
     */
    public SystemJob() {
    }

    public SystemJob(String className) {
        this.className = className;
    }

    /*
         * (non-Javadoc)
         *
         * @see ecoo.data.BaseModel#getPrimaryId()
         */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @JsonGetter
    public String getSimpleClassName() {
        if (this.className == null) {
            return null;
        } else {
            return this.className.substring(this.className.lastIndexOf(".") + 1);
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @JsonGetter
    public String getProcessingTimeDescription() {
        if (this.endTime == null) {
            return "";
        } else {
            return TimeDescriptionBuilder.aTimeDescription()
                    .withEvaluationDate(this.endTime)
                    .build();
        }
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public String getSuccessfulProcessingInd() {
        return successfulProcessingInd;
    }

    public void setSuccessfulProcessingInd(String successfulProcessingInd) {
        this.successfulProcessingInd = successfulProcessingInd;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "SystemJob{" +
                "primaryId=" + primaryId +
                ", className='" + className + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", processingTime=" + processingTime +
                ", successfulProcessingInd='" + successfulProcessingInd + '\'' +
                ", comments=" + comments +
                '}';
    }
}