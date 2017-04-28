package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class RegisterUserAccountResponseBuilder {
    private String processInstanceId;
    private String businessKey;
    private Integer requestingUserId;
    private String requestingUser;

    private RegisterUserAccountResponseBuilder() {
    }

    public static RegisterUserAccountResponseBuilder aRegisterUserAccountResponse() {
        return new RegisterUserAccountResponseBuilder();
    }

    public RegisterUserAccountResponseBuilder withProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public RegisterUserAccountResponseBuilder withBusinessKey(String businessKey) {
        this.businessKey = businessKey;
        return this;
    }

    public RegisterUserAccountResponseBuilder withRequestingUserId(Integer requestingUserId) {
        this.requestingUserId = requestingUserId;
        return this;
    }

    public RegisterUserAccountResponseBuilder withRequestingUser(String requestingUser) {
        this.requestingUser = requestingUser;
        return this;
    }

    public RegisterUserAccountResponse build() {
        return new RegisterUserAccountResponse(processInstanceId, businessKey, requestingUserId, requestingUser);
    }
}
