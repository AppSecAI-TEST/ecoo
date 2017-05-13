package ecoo.ws.user.rest;

import ecoo.log.aspect.ProfileExecution;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.user.rest.json.CreateSignatureRequest;
import ecoo.ws.user.rest.json.CreateSignatureResponse;
import ecoo.ws.user.rest.json.CreateSignatureResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/signatures")
public class SignatureResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureResource.class.getSimpleName());

    @ProfileExecution
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CreateSignatureResponse> create(@RequestBody CreateSignatureRequest request) {
        Assert.notNull(request, "The variable request cannot be null.");
        Assert.hasText(request.getPersonalReference(), "System cannot complete request. The personal reference " +
                "value is required or cannot be blank.");
        Assert.hasText(request.getFirsName(), "System cannot complete request. The first name" +
                "value is required or cannot be blank.");
        Assert.hasText(request.getLastName(), "System cannot complete request. The last name " +
                "value is required or cannot be blank.");
        Assert.hasText(request.getBase64Payload(), "System cannot complete request. The base64 payload " +
                "value is required or cannot be blank.");

        LOG.info(request.toString());

        return ResponseEntity.ok(CreateSignatureResponseBuilder.aRegisterSignatureResponse()
                .withPersonalReference(request.getPersonalReference())
                .withFirsName(request.getFirsName())
                .withLastName(request.getLastName())
                .withBase64Payload(request.getBase64Payload())
                .withSuccessfulInd(true)
                .build());
    }
}