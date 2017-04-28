package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ecoo.builder.TimeDescriptionBuilder;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "metric")
public class Metric extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 4810090749464222604L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "user_id")
    @Audited
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    @Audited
    private MetricType metricType;

    @Column(name = "value")
    @Audited
    private String value;

    @Column(name = "last_updated")
    @Audited
    private Date lastUpdated;

    public Metric() {
    }

    public Metric(Integer userId, MetricType metricType, String value, Date lastUpdated) {
        this.userId = userId;
        this.metricType = metricType;
        this.value = value;
        this.lastUpdated = lastUpdated;
    }

    @JsonIgnore
    public boolean isType(MetricType.Type type) {
        return Objects.equals(type.getPrimaryId(), this.metricType.getPrimaryId());
    }

    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public MetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonGetter
    public String getLastUpdatedDescription() {
        if (this.lastUpdated == null) return null;
        final DateTime dateClosed = new DateTime().withMillis(this.lastUpdated.getTime());
        return TimeDescriptionBuilder.aTimeDescription()
                .witEvaluationDate(dateClosed)
                .build();
    }


    @Override
    public String toString() {
        return "Metric{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
                ", metricType=" + metricType +
                ", value='" + value + '\'' +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}