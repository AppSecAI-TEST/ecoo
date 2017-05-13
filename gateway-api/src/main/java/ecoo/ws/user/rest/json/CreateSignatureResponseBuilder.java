package ecoo.ws.user.rest.json;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public final class CreateSignatureResponseBuilder {
    private String personalReference;
    private String firsName;
    private String lastName;
    private String base64Payload;
    private boolean successfulInd;

    private CreateSignatureResponseBuilder() {
    }

    public static CreateSignatureResponseBuilder aRegisterSignatureResponse() {
        return new CreateSignatureResponseBuilder();
    }

    public CreateSignatureResponseBuilder withPersonalReference(String personalReference) {
        this.personalReference = personalReference;
        return this;
    }

    public CreateSignatureResponseBuilder withFirsName(String firsName) {
        this.firsName = firsName;
        return this;
    }

    public CreateSignatureResponseBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CreateSignatureResponseBuilder withBase64Payload(String base64Payload) {
        this.base64Payload = base64Payload;
        return this;
    }

    public CreateSignatureResponseBuilder withSuccessfulInd(boolean successfulInd) {
        this.successfulInd = successfulInd;
        return this;
    }

    public CreateSignatureResponse build() {
        CreateSignatureResponse createSignatureResponse = new CreateSignatureResponse();
        createSignatureResponse.setPersonalReference(personalReference);
        createSignatureResponse.setFirsName(firsName);
        createSignatureResponse.setLastName(lastName);
        createSignatureResponse.setBase64Payload(base64Payload);
        createSignatureResponse.setSuccessfulInd(successfulInd);
        return createSignatureResponse;
    }
}
