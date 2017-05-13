package ecoo.ws.user.rest.json;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public final class RegisterSignatureResponseBuilder {
    private String personalReference;
    private String firsName;
    private String lastName;
    private String base64Payload;
    private boolean successfulInd;

    private RegisterSignatureResponseBuilder() {
    }

    public static RegisterSignatureResponseBuilder aRegisterSignatureResponse() {
        return new RegisterSignatureResponseBuilder();
    }

    public RegisterSignatureResponseBuilder withPersonalReference(String personalReference) {
        this.personalReference = personalReference;
        return this;
    }

    public RegisterSignatureResponseBuilder withFirsName(String firsName) {
        this.firsName = firsName;
        return this;
    }

    public RegisterSignatureResponseBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public RegisterSignatureResponseBuilder withBase64Payload(String base64Payload) {
        this.base64Payload = base64Payload;
        return this;
    }

    public RegisterSignatureResponseBuilder withSuccessfulInd(boolean successfulInd) {
        this.successfulInd = successfulInd;
        return this;
    }

    public RegisterSignatureResponse build() {
        RegisterSignatureResponse registerSignatureResponse = new RegisterSignatureResponse();
        registerSignatureResponse.setPersonalReference(personalReference);
        registerSignatureResponse.setFirsName(firsName);
        registerSignatureResponse.setLastName(lastName);
        registerSignatureResponse.setBase64Payload(base64Payload);
        registerSignatureResponse.setSuccessfulInd(successfulInd);
        return registerSignatureResponse;
    }
}
