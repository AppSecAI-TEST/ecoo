package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.envers.Audited;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "chamber")
public class Chamber extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 8647765913897173175L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Integer primaryId;

    @Column(name = "name")
    @Audited
    private String name;

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

    @Column(name = "fax_no")
    @Audited
    private String faxNo;

    @Column(name = "email")
    @Audited
    private String email;

    @Column(name = "website")
    @Audited
    private String website;


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

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
        return "Chamber{" +
                "primaryId=" + primaryId +
                ", name='" + name + '\'' +
                ", building='" + building + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", province=" + province +
                ", countryId='" + countryId + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", faxNo='" + faxNo + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}