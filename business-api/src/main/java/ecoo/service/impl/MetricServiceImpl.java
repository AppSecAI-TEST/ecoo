package ecoo.service.impl;

import ecoo.builder.MetricBuilder;
import ecoo.dao.MetricDao;
import ecoo.data.Metric;
import ecoo.data.MetricType;
import ecoo.data.ShipmentStatus;
import ecoo.service.DataService;
import ecoo.service.MetricService;
import ecoo.service.ShipmentService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service
public final class MetricServiceImpl extends AuditTemplate<Integer, Metric, MetricDao> implements MetricService {

    private final DataService dataService;

    private final ShipmentService shipmentService;

    /**
     * Constructs a new {@link MetricServiceImpl} service object.
     */
    @Autowired
    public MetricServiceImpl(MetricDao metricDao, DataService dataService, ShipmentService shipmentService) {
        super(metricDao);
        this.dataService = dataService;
        this.shipmentService = shipmentService;
    }

    /**
     * Returns ALL the metrics for the the given user.
     *
     * @param userId The pk of the user to evaluate.
     * @return The metrics.
     */
    @Override
    public List<Metric> findMetricsByUser(Integer userId) {
        Assert.notNull(userId, "Variable userId cannot be null.");

        final DateTime now = DateTime.now();
        final DateTime beginningOfMonth = now.withDayOfMonth(1).withTimeAtStartOfDay();

        final List<Metric> metrics = new ArrayList<>();

        final long shipmentCountLastMonthCount = shipmentService.count(userId, beginningOfMonth.minusMonths(1), beginningOfMonth, ShipmentStatus.NewAndPendingSubmission
                , ShipmentStatus.SubmittedAndPendingChamberApproval, ShipmentStatus.Approved, ShipmentStatus.Declined);
        final Metric shipmentCountLastMonthMetric = MetricBuilder.aMetric()
                .withUserId(userId)
                .withMetricType(dataService.metricTypeById(MetricType.Type.ShipmentCountLastMonth.getPrimaryId()))
                .withValue(String.valueOf(shipmentCountLastMonthCount))
                .withLastUpdated(now.toDate())
                .build();
        metrics.add(shipmentCountLastMonthMetric);

        final long shipmentCountCurrentMonthCount = shipmentService.count(userId, beginningOfMonth, beginningOfMonth.plusMonths(1), ShipmentStatus.NewAndPendingSubmission
                , ShipmentStatus.SubmittedAndPendingChamberApproval, ShipmentStatus.Approved, ShipmentStatus.Declined);
        final Metric shipmentCountCurrentMonthMetric = MetricBuilder.aMetric()
                .withUserId(userId)
                .withMetricType(dataService.metricTypeById(MetricType.Type.ShipmentCountCurrentMonth.getPrimaryId()))
                .withValue(String.valueOf(shipmentCountCurrentMonthCount))
                .withLastUpdated(now.toDate())
                .build();
        metrics.add(shipmentCountCurrentMonthMetric);

        final long openShipmentCount = shipmentService.count(userId, ShipmentStatus.NewAndPendingSubmission, ShipmentStatus.SubmittedAndPendingChamberApproval);
        final Metric openShipmentCountMetric = MetricBuilder.aMetric()
                .withUserId(userId)
                .withMetricType(dataService.metricTypeById(MetricType.Type.OpenShipmentCount.getPrimaryId()))
                .withValue(String.valueOf(openShipmentCount))
                .withLastUpdated(now.toDate())
                .build();
        metrics.add(openShipmentCountMetric);

        return metrics;
    }
}
