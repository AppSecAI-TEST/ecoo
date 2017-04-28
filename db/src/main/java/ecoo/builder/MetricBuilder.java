package ecoo.builder;

import ecoo.data.Metric;
import ecoo.data.MetricType;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class MetricBuilder {

    private Integer primaryId;
    private Integer userId;
    private MetricType metricType;
    private String value;
    private Date lastUpdated;

    private MetricBuilder() {
    }

    public static MetricBuilder aMetric() {
        return new MetricBuilder();
    }

    public MetricBuilder withPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
        return this;
    }

    public MetricBuilder withUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public MetricBuilder withMetricType(MetricType metricType) {
        this.metricType = metricType;
        return this;
    }

    public MetricBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public MetricBuilder withLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public Metric build() {
        Metric metric = new Metric();
        metric.setPrimaryId(primaryId);
        metric.setUserId(userId);
        metric.setMetricType(metricType);
        metric.setValue(value);
        metric.setLastUpdated(lastUpdated);
        return metric;
    }
}
