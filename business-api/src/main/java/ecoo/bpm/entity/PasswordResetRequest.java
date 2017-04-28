package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;
import ecoo.data.User;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PasswordResetRequest extends WorkflowRequest implements Serializable {

    private static final long serialVersionUID = -8051061055220541523L;

    private User user;

    private String plainTextPassword;

    private boolean forcePasswordExpired;

    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.PasswordReset;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public boolean isForcePasswordExpired() {
        return forcePasswordExpired;
    }

    public void setForcePasswordExpired(boolean forcePasswordExpired) {
        this.forcePasswordExpired = forcePasswordExpired;
    }
}
