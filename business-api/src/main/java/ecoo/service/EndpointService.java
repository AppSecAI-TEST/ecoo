package ecoo.service;

import ecoo.data.Endpoint;
import ecoo.data.EndpointStat;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface EndpointService extends CrudService<Integer, Endpoint> {

    /**
     * Returns all the endpoint stats for the given API.
     *
     * @param endpointId        The API id.
     * @param effectiveFromDate The from which to start.
     * @return A collection.
     */
    Collection<EndpointStat> findByApiAndAfterRequestedDate(Integer endpointId, Date effectiveFromDate);

    /**
     * Returns all the health stats for the given API.
     *
     * @param endpointId The endpoint to evaluate.
     * @return A collection.
     */
    Collection<EndpointStat> findStatsByEndpoint(Integer endpointId);
}
