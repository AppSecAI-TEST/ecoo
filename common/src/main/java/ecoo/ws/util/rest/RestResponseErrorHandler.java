package ecoo.ws.util.rest;

import ecoo.util.BusinessRuleViolationException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

/**
 * This adds the Message and ErrorMessage headers or the body that the server returns to the exception,
 * since that often contains the real reason why the call failed.
 *
 * @author Justin Rundle
 * @since February 2017
 */
public class RestResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse clienthttpresponse) throws IOException {
        final StringBuilder msg = new StringBuilder();
        appendHeader(clienthttpresponse, msg, "ErrorMessage");
        appendHeader(clienthttpresponse, msg, "Message");
        msg.append(IOUtils.toString(clienthttpresponse.getBody()));
        appendHeader(clienthttpresponse, msg, "Url");

        //RestClientException
        throw new BusinessRuleViolationException(format("[%s] %S \n%s", clienthttpresponse.getStatusCode(),
                clienthttpresponse.getStatusText(),
                msg.toString()
        ));
    }

    private void appendHeader(final ClientHttpResponse clienthttpresponse, final StringBuilder msg, final String headerName) {
        final List<String> values = clienthttpresponse.getHeaders().get(headerName);
        if (values != null && values.size() > 0 && values.get(0) != null) {
            msg.append(" \n");
            msg.append(headerName);
            msg.append(": \n");
            for (String value : values) {
                msg.append(value);
                msg.append(" \n");
            }
        }
    }

    @Override
    public boolean hasError(final ClientHttpResponse clienthttpresponse) throws IOException {
        if (!clienthttpresponse.getStatusCode().is2xxSuccessful()) {
            return true;
        }
        return false;
    }
}