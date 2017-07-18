package ecoo.job;

import ecoo.data.KnownUser;
import ecoo.data.SystemJob;
import ecoo.data.User;
import ecoo.security.UserAuthentication;
import ecoo.service.SystemJobService;
import ecoo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * The base processor to enforce base behaviour.
 *
 * @author Justin Rundle
 * @since April 2017
 */
public abstract class BaseScheduledTask implements ScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(BaseScheduledTask.class);

    private UserService userService;

    private SystemJobService systemJobService;

    public BaseScheduledTask(UserService userService, SystemJobService systemJobService) {
        this.userService = userService;
        this.systemJobService = systemJobService;
    }

    @Override
    public void execute() {
        LOG.info("--- execute ---");

        final User BatchProcessorAccount = userService.findById(KnownUser.BatchProcessorAccount.getPrimaryId());
        Assert.notNull(BatchProcessorAccount, "BatchProcessorAccount not found.");

        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(BatchProcessorAccount));

        final SystemJob systemJob = new SystemJob(getClass().getCanonicalName());
        try {
            systemJobService.register(systemJob);

            try {
                run();
                markAsSuccessful(systemJob);

            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
                markAsFailed(systemJob, e);
            }
            systemJobService.save(systemJob);

        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            markAsFailed(systemJob, e);
            systemJobService.save(systemJob);
        }
    }
    
    private void markAsSuccessful(SystemJob systemJob) {
        systemJob.setEndTime(new Date());
        systemJob.setSuccessfulProcessingInd("Y");
    }

    private void markAsFailed(SystemJob systemJob, Exception e) {
        systemJob.setEndTime(new Date());
        systemJob.setSuccessfulProcessingInd("N");
        systemJob.setComments(e.getMessage());
    }

    /**
     * Method to actually perform the job execution.
     *
     * @throws Exception If anything goes wrong.
     */
    protected abstract void run() throws Exception;

}
