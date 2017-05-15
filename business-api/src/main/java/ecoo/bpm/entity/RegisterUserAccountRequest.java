package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;
import ecoo.data.Chamber;
import ecoo.data.Company;
import ecoo.data.Signature;
import ecoo.data.User;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class RegisterUserAccountRequest extends WorkflowRequest implements Serializable {

    private static final long serialVersionUID = -5636726554980365222L;

    private Company company;

    private User user;

    private Chamber chamber;

    private String plainTextPassword;

    private String source;

    private Signature signature;

    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.UserRegistration;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Chamber getChamber() {
        return chamber;
    }

    public void setChamber(Chamber chamber) {
        this.chamber = chamber;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "RegisterUserAccountRequest{" +
                "company=" + company +
                ", user=" + user +
                ", chamber=" + chamber +
                ", plainTextPassword='" + plainTextPassword + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}