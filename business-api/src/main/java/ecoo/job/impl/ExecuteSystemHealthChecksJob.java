package ecoo.job.impl;

import ecoo.command.ExecuteEndpointHealthCommand;
import ecoo.job.BaseScheduledTask;
import ecoo.service.SystemJobService;
import ecoo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * The scheduled job to execute system health checks.
 *
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class ExecuteSystemHealthChecksJob extends BaseScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteSystemHealthChecksJob.class);

    private ExecuteEndpointHealthCommand executeEndpointHealthCommand;

    @Autowired
    public ExecuteSystemHealthChecksJob(UserService userService, SystemJobService systemJobService, ExecuteEndpointHealthCommand executeEndpointHealthCommand) {
        super(userService, systemJobService);
        this.executeEndpointHealthCommand = executeEndpointHealthCommand;
    }

    // Fire at 02:00am every Sunday to Thursday
    @Scheduled(cron = "0 0/30 * * * ?")
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Method to actually perform the job execution.
     *
     * @throws Exception If anything goes wrong.
     */
    @Override
    protected void run() throws Exception {
        LOG.info("--- run ---");
        executeEndpointHealthCommand.execute();
    }
}