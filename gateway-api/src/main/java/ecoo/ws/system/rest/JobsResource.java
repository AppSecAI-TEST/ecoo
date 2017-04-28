package ecoo.ws.system.rest;

import ecoo.data.SystemJob;
import ecoo.job.BaseScheduledTask;
import ecoo.service.SystemJobService;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.system.json.SystemJobDetailsResponse;
import ecoo.ws.system.json.SystemJobStatsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/api/jobs")
public class JobsResource extends BaseResource implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(JobsResource.class);

    private SystemJobService systemJobService;

    private ApplicationContext applicationContext;

    @Autowired
    public JobsResource(SystemJobService systemJobService) {
        this.systemJobService = systemJobService;
    }

    @RequestMapping(value = "/execute/id/{id}", method = RequestMethod.GET
            , headers = "Accept=application/json"
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<SystemJob> execute(@PathVariable Integer id) {
        final SystemJob systemJob = systemJobService.findById(id);
        final String beanId = camelCase(systemJob.getSimpleClassName());
        LOG.info("Retrieve job with bean id <{}>", beanId);

        final BaseScheduledTask baseScheduledTask = (BaseScheduledTask) applicationContext.getBean(beanId);
        baseScheduledTask.execute();

        return ResponseEntity.ok(systemJob);
    }

    private String camelCase(final String word) {
        return word.substring(0, 1).toLowerCase() + word.substring(1);
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public ResponseEntity<SystemJobStatsResponse> stats() {
        final Set<SystemJob> jobs = new TreeSet<>(Comparator.comparing(SystemJob::getClassName));
        jobs.addAll(systemJobService.findMostRecentJobs());

        return ResponseEntity.ok(new SystemJobStatsResponse(jobs));
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Set<SystemJob>> findMostRecentJobs() {
        final Set<SystemJob> jobs = new TreeSet<>(Comparator.comparing(SystemJob::getClassName));
        jobs.addAll(systemJobService.findMostRecentJobs());

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/className/{className}", method = RequestMethod.GET)
    public ResponseEntity<Set<SystemJob>> findAllJobByClassName(@PathVariable String className) {
        Assert.hasText(className);
        final Set<SystemJob> jobs = new TreeSet<>((o1, o2)
                -> o2.getStartTime().compareTo(o1.getStartTime()));
        jobs.addAll(systemJobService.findByClassName(className));

        return ResponseEntity.ok(jobs);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<SystemJob> findById(@PathVariable Integer id) {
        Assert.notNull(id);
        return ResponseEntity.ok(systemJobService.findById(id));
    }

    @RequestMapping(value = "/details/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<SystemJobDetailsResponse> findJobDetails(@PathVariable Integer id) {
        Assert.notNull(id);
        final SystemJob systemJob = systemJobService.findById(id);

        final Set<SystemJob> jobs = new TreeSet<>((o1, o2)
                -> o2.getStartTime().compareTo(o1.getStartTime()));
        jobs.addAll(systemJobService.findByClassName(systemJob.getClassName()));

        return ResponseEntity.ok(new SystemJobDetailsResponse(systemJob, jobs));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
