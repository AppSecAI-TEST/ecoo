package ecoo.ws.system.json;


import ecoo.data.SystemJob;

import java.util.Set;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class SystemJobDetailsResponse {

    private SystemJob systemJob;

    private final Set<SystemJob> history;

    public SystemJobDetailsResponse(SystemJob systemJob, Set<SystemJob> history) {
        this.systemJob = systemJob;
        this.history = history;
    }

    public SystemJob getSystemJob() {
        return systemJob;
    }

    public Set<SystemJob> getHistory() {
        return history;
    }
}
