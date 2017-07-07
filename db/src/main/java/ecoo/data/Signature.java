
package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Entity
@Table(name = "signature")
public class Signature extends BaseModel<Integer> {

    private static final long serialVersionUID = 6858773528691365788L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "personal_ref_value")
    @Audited
    private String personalRefValue;

    @Column(name = "first_name")
    @Audited
    private String firstName;

    @Column(name = "last_name")
    @Audited
    private String lastName;

    @Column(name = "company_name")
    @Audited
    private String companyName;

    @Column(name = "encoded_image")
    @Audited
    private String encodedImage;

    @Column(name = "date_created")
    @Audited
    private Date dateCreated;

    @Column(name = "user_signature_id")
    @Audited
    private Integer userSignatureId;


    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    public String getPersonalRefValue() {
        return personalRefValue;
    }

    public void setPersonalRefValue(String personalRefValue) {
        this.personalRefValue = personalRefValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    // DEVOTE: Only used for auditing purposes
    @JsonIgnore
    public Integer getUserSignatureId() {
        return userSignatureId;
    }

    public void setUserSignatureId(Integer userSignatureId) {
        this.userSignatureId = userSignatureId;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "primaryId=" + primaryId +
                ", personalRefValue='" + personalRefValue + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", encodedImage='" + encodedImage + '\'' +
                ", dateCreated=" + dateCreated +
                ", userSignatureId=" + userSignatureId +
                '}';
    }
}