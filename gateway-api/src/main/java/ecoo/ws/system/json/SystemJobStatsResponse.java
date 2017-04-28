package ecoo.ws.system.json;


import ecoo.data.SystemJob;

import java.util.Set;

/**
 * @author Justin Rundle
 * @since July 2016
 */
public class SystemJobStatsResponse {

    private final Set<SystemJob> jobs;

    private SystemJob longestRunningJob;

    private SystemJob oldestJob;

    public SystemJobStatsResponse(Set<SystemJob> jobs) {
        this.jobs = jobs;

        for (final SystemJob job : jobs) {
            if (this.longestRunningJob == null) {
                this.longestRunningJob = job;
            } else if (job.getProcessingTime() > this.longestRunningJob.getProcessingTime()) {
                this.longestRunningJob = job;
            }

            if (this.oldestJob == null) {
                this.oldestJob = job;
            } else if (job.getStartTime().before(this.oldestJob.getStartTime())) {
                this.oldestJob = job;
            }
        }
    }

    public Set<SystemJob> getJobs() {
        return jobs;
    }

    public SystemJob getLongestRunningJob() {
        return longestRunningJob;
    }

    public SystemJob getOldestJob() {
        return oldestJob;
    }
}
