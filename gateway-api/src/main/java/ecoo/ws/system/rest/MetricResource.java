package ecoo.ws.system.rest;


import ecoo.data.Metric;
import ecoo.log.aspect.ProfileExecution;
import ecoo.service.MetricService;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Justin Rundle
 * @since March 2017
 */
@RestController
@RequestMapping("/api/metrics")
public class MetricResource extends BaseResource {

    private MetricService metricService;

    @Autowired
    public MetricResource(MetricService metricService) {
        this.metricService = metricService;
    }

    @ProfileExecution
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Metric>> findMetricsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(metricService.findMetricsByUser(userId));
    }
}
