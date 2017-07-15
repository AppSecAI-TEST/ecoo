package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class RegisterCompanyAccountResponseBuilder {

    private String processInstanceId;

    private String businessKey;

    private Integer requestingUserId;

    private String requestingUser;

    private RegisterCompanyAccountResponseBuilder() {
    }

    public static RegisterCompanyAccountResponseBuilder aRegisterCompanyAccountResponse() {
        return new RegisterCompanyAccountResponseBuilder();
    }

    public RegisterCompanyAccountResponseBuilder withProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public RegisterCompanyAccountResponseBuilder withBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public RegisterCompanyAccountResponseBuilder withRequestingUserId(Integer requestingUserId) {
        this.requestingUserId = requestingUserId;
        return this;
    }

    public RegisterCompanyAccountResponseBuilder withRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
        return this;
    }

    public RegisterCompanyAccountResponse build() {
        RegisterCompanyAccountResponse registerCompanyAccountResponse = new RegisterCompanyAccountResponse(processInstanceId, businessKey, requestingUserId, requestingUser);
        return registerCompanyAccountResponse;
    }
}
