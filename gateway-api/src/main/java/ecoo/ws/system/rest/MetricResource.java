package ecoo.ws.system.rest;


import ecoo.data.Metric;
import ecoo.log.aspect.ProfileExecution;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Justin Rundle
 * @since March 2017
 */
@RestController
@RequestMapping("/api/metrics")
public class MetricResource extends BaseResource {

    @ProfileExecution
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<Metric>> findAdministrationMetricsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @ProfileExecution
    @RequestMapping(value = "/user/{userId}/reload", method = RequestMethod.GET)
    public ResponseEntity<List<Metric>> reloadUserMetrics(@PathVariable Integer userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @ProfileExecution
    @RequestMapping(value = "/reload", method = RequestMethod.GET)
    public ResponseEntity<Collection<Integer>> reloadAllUserMetrics() {
        return ResponseEntity.ok(new ArrayList<>());
    }
}
