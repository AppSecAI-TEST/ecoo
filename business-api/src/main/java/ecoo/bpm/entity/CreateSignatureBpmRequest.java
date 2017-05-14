package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class CreateSignatureBpmRequest extends WorkflowRequest implements Serializable {

    private String personalReference;

    private String firsName;

    private String lastName;

    private String base64Payload;

    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.CreateSignatureRequest;
    }

    public String getPersonalReference() {
        return personalReference;
    }

    public void setPersonalReference(String personalReference) {
        this.personalReference = personalReference;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBase64Payload() {
        return base64Payload;
    }

    public void setBase64Payload(String base64Payload) {
        this.base64Payload = base64Payload;
    }

    @Override
    public String toString() {
        return "CreateSignatureBpmRequest{" +
                "personalReference='" + personalReference + '\'' +
                ", firsName='" + firsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", base64Payload='" + base64Payload + '\'' +
                '}';
    }
}