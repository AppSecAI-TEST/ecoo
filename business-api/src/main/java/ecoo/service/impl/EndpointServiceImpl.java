package ecoo.service.impl;

import ecoo.dao.EndpointDao;
import ecoo.data.Endpoint;
import ecoo.data.EndpointStat;
import ecoo.service.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Service
public class EndpointServiceImpl extends JdbcTemplateService<Integer, Endpoint> implements EndpointService {

    private EndpointDao endpointDao;

    @Autowired
    public EndpointServiceImpl(EndpointDao endpointDao) {
        super(endpointDao);
        this.endpointDao = endpointDao;
    }

    /**
     * Returns all the endpoint stats for the given API.
     *
     * @param endpointId        The API id.
     * @param effectiveFromDate The from which to start.
     * @return A collection.
     */
    @Override
    public Collection<EndpointStat> findByApiAndAfterRequestedDate(Integer endpointId, Date effectiveFromDate) {
        return endpointDao.findByApiAndAfterRequestedDate(endpointId, effectiveFromDate);
    }

    /**
     * Returns all the health stats for the given API.
     *
     * @param endpointId The endpoint to evaluate.
     * @return A collection.
     */
    @Override
    public Collection<EndpointStat> findStatsByEndpoint(Integer endpointId) {
        Assert.notNull(endpointId);
        return endpointDao.findStatsByEndpoint(endpointId);
    }

    /**
     * Method to insert or update the given entity.
     *
     * @param entity The entity to save.
     * @return The saved entity.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Endpoint save(Endpoint entity) {
        final Endpoint endpoint = super.save(entity);

        final EndpointStat endpointStat = new EndpointStat();
        endpointStat.setApiId(endpoint.getPrimaryId());
        endpointStat.setRequestedTime(endpoint.getRequestedTime());
        endpointStat.setResponse(endpoint.getResponse());
        endpointStat.setStatus(endpoint.getStatus());
        endpointDao.save(endpointStat);

        return endpoint;
    }
}
