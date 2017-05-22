package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class ForgotPasswordRequest extends WorkflowRequest implements Serializable {

    private static final long serialVersionUID = 506980043786095256L;

    private String emailAddress;

    private String username;
    
    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.ForgotPassword;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequest{" +
                "emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
