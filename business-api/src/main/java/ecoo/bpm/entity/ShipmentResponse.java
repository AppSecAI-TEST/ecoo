package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class ShipmentResponse {

    private String processInstanceId;

    private String businessKey;

    private Integer requestingUserId;

    private String requestingUser;

    @JsonCreator
    public ShipmentResponse(@JsonProperty("processInstanceId") String processInstanceId
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
        return "ShipmentResponse{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", requestingUserId=" + requestingUserId +
                ", requestingUser='" + requestingUser + '\'' +
                '}';
    }
}