package ecoo.ws.user.rest;

import ecoo.data.Signature;
import ecoo.data.audit.Revision;
import ecoo.log.aspect.ProfileExecution;
import ecoo.service.SignatureService;
import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.user.rest.json.CreateSignatureRequest;
import ecoo.ws.user.rest.json.CreateSignatureResponse;
import ecoo.ws.user.rest.json.CreateSignatureResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/signatures")
public class SignatureResource extends BaseResource {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureResource.class.getSimpleName());

    private SignatureService signatureService;

    @Autowired
    public SignatureResource(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @ProfileExecution
    @RequestMapping(value = "/bulk", method = RequestMethod.POST)
    public ResponseEntity<Signature[]> createBulk(@RequestBody Signature[] signatures) {
        LOG.info(signatures.toString());
        for (Signature signature : signatures) {
              signature = signatureService.findByPersonalReference(signature.getPersonalRefValue());
            if (signature == null) {
                LOG.info("No signature found for personalRef <{}> >> creating new entry.", signature.getPersonalRefValue());
                signature = new Signature();

            } else {
                LOG.info("Found signature for personalRef <{}> >> updating new entry {}.", signature.getPersonalRefValue()
                        , signature.getPrimaryId());
            }

            signature.setPersonalRefValue(signature.getPersonalRefValue());
            signature.setFirstName(signature.getFirstName());
            signature.setLastName(signature.getLastName());
            signature.setCompanyName(signature.getCompanyName());
            signature.setEncodedImage(signature.getEncodedImage());

            signatureService.save(signature);
        }
        return ResponseEntity.ok(signatures);
    }

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
        Assert.hasText(request.getCompanyName(), "System cannot complete request. The company name " +
                "value is required or cannot be blank.");
        Assert.hasText(request.getBase64Payload(), "System cannot complete request. The base64 payload " +
                "value is required or cannot be blank.");

        LOG.info(request.toString());

        Signature signature = signatureService.findByPersonalReference(request.getPersonalReference());
        if (signature == null) {
            LOG.info("No signature found for personalRef <{}> >> creating new entry.", request.getPersonalReference());
            signature = new Signature();

        } else {
            LOG.info("Found signature for personalRef <{}> >> updating new entry {}.", request.getPersonalReference()
                    , signature.getPrimaryId());
        }

        signature.setPersonalRefValue(request.getPersonalReference());
        signature.setFirstName(request.getFirsName());
        signature.setLastName(request.getLastName());
        signature.setCompanyName(request.getCompanyName());
        signature.setEncodedImage(request.getBase64Payload());

        signatureService.save(signature);

        return ResponseEntity.ok(CreateSignatureResponseBuilder.aRegisterSignatureResponse()
                .withPersonalReference(request.getPersonalReference())
                .withFirsName(request.getFirsName())
                .withLastName(request.getLastName())
                .withCompanyName(request.getCompanyName())
                .withBase64Payload(request.getBase64Payload())
                .withSuccessfulInd(true)
                .build());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Signature>> findAll() {
        return ResponseEntity.ok(signatureService.findAll());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Signature> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(signatureService.findById(id));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Signature> delete(@PathVariable Integer id) {
        final Signature entity = signatureService.findById(id);
        return ResponseEntity.ok(signatureService.delete(entity));
    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final Signature entity = signatureService.findById(id);
        return ResponseEntity.ok(signatureService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final Signature entity = signatureService.findById(id);
        return ResponseEntity.ok(signatureService.findModifiedBy(entity));
    }
}