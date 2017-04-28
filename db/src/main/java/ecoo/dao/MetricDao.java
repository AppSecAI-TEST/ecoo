package ecoo.dao;


import ecoo.data.Metric;
import ecoo.data.MetricType;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface MetricDao extends AuditLogDao<Integer, Metric> {

    /**
     * Returns ALL the metrics for the the given user.
     *
     * @param userId The pk of the user to evaluate.
     * @return The metrics.
     */
    List<Metric> findByUser(Integer userId);

    /**
     * Returns a list for the given metric type.
     *
     * @param metricType The metric type to evaluate.
     * @return A list.
     */
    List<Metric> findByType(MetricType.Type metricType);

    /**
     * Returns the first element in the list for the given metric type.
     *
     * @param metricType The metric type to evaluate.
     * @return A metric or null.
     */
    Metric findFirstByType(MetricType.Type metricType);


    /**
     * Returns the metric for the the given user and type.
     *
     * @param userId     The pk of the user to evaluate.
     * @param metricType The metric type evaluate.
     * @return The metrics.
     */
    Metric findByUserAndType(Integer userId, MetricType.Type metricType);
}