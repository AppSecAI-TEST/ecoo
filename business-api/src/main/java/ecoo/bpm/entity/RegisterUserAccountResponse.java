package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class RegisterUserAccountResponse {

    private String processInstanceId;

    private String businessKey;

    private Integer requestingUserId;

    private String requestingUser;

    @JsonCreator
    public RegisterUserAccountResponse(@JsonProperty("processInstanceId") String processInstanceId
            , @JsonProperty("businessKey") String businessKey
            , @JsonProperty("requestingUserId") Integer requestingUserId
            , @JsonProperty("requestingUser") String requestingUser) {
        this.processInstanceId = processInstanceId;
        this.businessKey = businessKey;
        this.requestingUserId = requestingUserId;
        this.requestingUser = requestingUser;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public Integer getRequestingUserId() {
        return requestingUserId;
    }

    public String getRequestingUser() {
        return requestingUser;
    }

    @Override
    public String toString() {
        return "RegisterUserAccountResponse{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", requestingUserId=" + requestingUserId +
                ", requestingUser='" + requestingUser + '\'' +
                '}';
    }
}