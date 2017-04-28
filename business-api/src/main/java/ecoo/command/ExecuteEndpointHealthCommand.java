package ecoo.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ecoo.data.Endpoint;
import ecoo.service.EndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class ExecuteEndpointHealthCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ExecuteEndpointHealthCommand.class);

    private EndpointService endpointService;

    @Autowired
    public ExecuteEndpointHealthCommand(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    public Collection<Endpoint> execute() {
        LOG.info("--- execute ---");

        final Collection<Endpoint> endpoints = endpointService.findAll();
        LOG.info("Executing {} APIs...", endpoints.size());

        final Date requestedTime = new Date();

        for (final Endpoint endpoint : endpoints) {
            submitRequest(endpoint, requestedTime);
            endpointService.save(endpoint);
        }
        return endpointService.findAll();
    }

    private void submitRequest(final Endpoint endpoint, final Date requestedTime) {
        LOG.info("Calling... " + endpoint.getUrl());
        endpoint.setRequestedTime(requestedTime);

        final RestTemplate restTemplate = new RestTemplate();
        try {
            final String json = restTemplate.getForObject(endpoint.getUrl(), String.class);
            endpoint.setResponse(json);
            endpoint.setStatus(Status.UP.getCode());

        } catch (final HttpServerErrorException e) {
            LOG.warn(e.getMessage(), e);

            endpoint.setResponse(e.getResponseBodyAsString());
            endpoint.setStatus(Status.DOWN.getCode());

        } catch (final Exception e) {
            final Health.Builder builder = new Health.Builder();
            builder.down(e);

            LOG.error(e.getMessage(), e);
            try {
                endpoint.setResponse(new ObjectMapper().writeValueAsString(builder.build()));
            } catch (JsonProcessingException e1) {
                LOG.error(e.getMessage(), e);
            }
            endpoint.setStatus(Status.DOWN.getCode());
        }
    }
}
