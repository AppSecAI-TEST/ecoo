package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class ForgotPasswordResponse implements Serializable {

    private static final long serialVersionUID = 5842533288403880237L;

    private String processInstanceId;

    private String emailAddress;

    @JsonCreator
    public ForgotPasswordResponse(@JsonProperty("processInstanceId") String processInstanceId
            , @JsonProperty("emailAddress") String emailAddress) {
        this.processInstanceId = processInstanceId;
        this.emailAddress = emailAddress;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "ForgotPasswordResponse{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
