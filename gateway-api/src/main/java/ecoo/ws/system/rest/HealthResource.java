package ecoo.ws.system.rest;

import ecoo.command.ExecuteEndpointHealthCommand;
import ecoo.data.Endpoint;
import ecoo.data.EndpointStat;
import ecoo.service.EndpointService;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.system.json.EndpointStatsHistogramResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController("HealthResource")
@RequestMapping("/api/health")
public class HealthResource extends BaseResource {

    @Autowired
    private EndpointService endpointService;

    @Autowired
    private ExecuteEndpointHealthCommand executeEndpointHealthCommand;

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public ResponseEntity<Collection<Endpoint>> checkHealth() {
        return ResponseEntity.ok(executeEndpointHealthCommand.execute());
    }

    @RequestMapping(value = "/apis", method = RequestMethod.GET)
    public ResponseEntity<Collection<Endpoint>> healthApis() {
        final Collection<Endpoint> apis = new TreeSet<>((o1, o2) -> o1.getName().compareTo(o2.getName()));
        apis.addAll(endpointService.findAll());
        return ResponseEntity.ok(apis);
    }

    @RequestMapping(value = "/stats/api/{api}", method = RequestMethod.GET)
    public ResponseEntity<Collection<EndpointStat>> healthStatsHistory(@PathVariable Integer api) {
        return ResponseEntity.ok(endpointService.findStatsByEndpoint(api));
    }

    @RequestMapping(value = "/stats/api/{api}/days/{days}", method = RequestMethod.GET)
    public ResponseEntity<Collection<EndpointStat>> healthStatsHistory(@PathVariable Integer api, @PathVariable Integer days) {
        final Date effectiveFromDate = new Date(DateTime.now().minusDays(days).getMillis());
        return ResponseEntity.ok(endpointService.findByApiAndAfterRequestedDate(api, effectiveFromDate));
    }

    @RequestMapping(value = "/histogram/stats/api/{api}/days/{days}", method = RequestMethod.GET)
    public ResponseEntity<EndpointStatsHistogramResponse> healthStatsHistogram(@PathVariable Integer api, @PathVariable Integer days) {
        final Date effectiveFromDate = new Date(DateTime.now().minusDays(days).getMillis());
        final Collection<EndpointStat> healthStats = new TreeSet<>((o1, o2)
                -> o1.getRequestedTime().compareTo(o2.getRequestedTime()));
        healthStats.addAll(endpointService.findByApiAndAfterRequestedDate(api, effectiveFromDate));

        return ResponseEntity.ok(new EndpointStatsHistogramResponse(effectiveFromDate, healthStats));
    }
}