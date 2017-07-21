package ecoo.service;

import ecoo.data.Metric;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface MetricService extends AuditedModelAware<Metric> {

    /**
     * Returns ALL the metrics for the the given user.
     *
     * @param userId The pk of the user to evaluate.
     * @return The metrics.
     */
    List<Metric> findMetricsByUser(Integer userId);
}
