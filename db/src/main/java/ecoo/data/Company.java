package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "company")
public class Company extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 8647765913897173175L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "name")
    @Audited
    private String name;

    @Column(name = "registration_no")
    @Audited
    private String registrationNo;

    @Column(name = "vat_no")
    @Audited
    private String vatNo;

    @Column(name = "type_id")
    @Audited
    private String typeId;

    @Column(name = "building")
    @Audited
    private String building;

    @Column(name = "street")
    @Audited
    private String street;

    @Column(name = "city")
    @Audited
    private String city;

    @Column(name = "postcode")
    @Audited
    private String postcode;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @Audited
    private Province province;

    @Column(name = "country_id")
    @Audited
    private String countryId;

    @Column(name = "phone_no")
    @Audited
    private String phoneNo;

    @Column(name = "email")
    @Audited
    private String email;

    @Column(name = "status")
    @Audited
    private String status;

    @JsonIgnore
    public boolean isInStatus(CompanyStatus companyStatus) {
        Assert.notNull(companyStatus);
        return companyStatus.id().equalsIgnoreCase(this.status);
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

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SuppressWarnings("Duplicates")
    @JsonGetter
    public String getDescription() {
        final StringBuilder buffer = new StringBuilder();

        if (!StringUtils.isEmpty(getBuilding())) {
            buffer.append(", ").append(getBuilding());
        }
        if (!StringUtils.isEmpty(getStreet())) {
            buffer.append(", ").append(getStreet());
        }

        if (!StringUtils.isEmpty(getCity())) {
            buffer.append(", ").append(getCity());
        }

        if (getProvince() != null) {
            buffer.append(", ").append(getProvince().getDescription());
        }

        if (!StringUtils.isEmpty(getPostcode())) {
            buffer.append(", ").append(getPostcode());
        }

        if (!StringUtils.isEmpty(getCountryId())) {
            buffer.append(", ").append(getCountryId());
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        return "Company{" +
                "primaryId=" + primaryId +
                ", name='" + name + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", vatNo='" + vatNo + '\'' +
                ", typeId='" + typeId + '\'' +
                ", building='" + building + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", province='" + province + '\'' +
                ", countryId='" + countryId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}