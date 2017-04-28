package ecoo.ws.system.json;

import ecoo.data.EndpointStat;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class EndpointStatsHistogramResponse {

    private final Collection<EndpointStat> stats;

    private final Date startDate;

    private Date endDate;

    public EndpointStatsHistogramResponse(Date startDate, Collection<EndpointStat> stats) {
        this.startDate = startDate;
        this.stats = stats;
        this.endDate = new Date();

        stats.stream().filter(stat -> stat.getRequestedTime().after(this.endDate)).forEach(stat -> {
            this.endDate = stat.getRequestedTime();
        });
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Collection<EndpointStat> getStats() {
        return stats;
    }
}
