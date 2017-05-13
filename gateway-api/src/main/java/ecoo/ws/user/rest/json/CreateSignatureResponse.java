package ecoo.ws.user.rest.json;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class CreateSignatureResponse {

    private String personalReference;

    private String firsName;

    private String lastName;

    private String base64Payload;

   private boolean successfulInd;

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

    public boolean isSuccessfulInd() {
        return successfulInd;
    }

    public void setSuccessfulInd(boolean successfulInd) {
        this.successfulInd = successfulInd;
    }

    @Override
    public String toString() {
        return "CreateSignatureResponse{" +
                "personalReference='" + personalReference + '\'' +
                ", firsName='" + firsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", base64Payload='" + base64Payload + '\'' +
                ", successfulInd=" + successfulInd +
                '}';
    }
}
