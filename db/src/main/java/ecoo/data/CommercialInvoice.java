package ecoo.data;

import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "doc_comm_inv")
@Document(type = "ecoo.commercial.invoice", indexName = "ecoo.commercial.invoice", shards = 1, replicas = 0)
public class CommercialInvoice extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 7031097514722247965L;

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "shipment_id")
    @Audited
    private Integer primaryId;

    @Column(name = "notif_party_name")
    @Audited
    private String notifyPartyName;

    @Column(name = "notif_party_building")
    @Audited
    private String notifyPartyBuilding;

    @Column(name = "notif_party_street")
    @Audited
    private String notifyPartyStreet;

    @Column(name = "notif_party_city")
    @Audited
    private String notifyPartyCity;

    @Column(name = "notif_party_postcode")
    @Audited
    private String notifyPartyPostcode;

    @Column(name = "notif_party_province")
    @Audited
    private String notifyPartyProvince;

    @Column(name = "notif_party_country")
    @Audited
    private String notifyPartyCountry;

    @Column(name = "notif_party_phone_no")
    @Audited
    private String notifyPartyPhoneNo;

    @Column(name = "notif_party_email")
    @Audited
    private String notifyPartyEmail;

    @Column(name = "pay_instruct")
    @Audited
    private String paymentInstruction;

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

    public String getNotifyPartyName() {
        return notifyPartyName;
    }

    public void setNotifyPartyName(String notifyPartyName) {
        this.notifyPartyName = notifyPartyName;
    }

    public String getNotifyPartyBuilding() {
        return notifyPartyBuilding;
    }

    public void setNotifyPartyBuilding(String notifyPartyBuilding) {
        this.notifyPartyBuilding = notifyPartyBuilding;
    }

    public String getNotifyPartyStreet() {
        return notifyPartyStreet;
    }

    public void setNotifyPartyStreet(String notifyPartyStreet) {
        this.notifyPartyStreet = notifyPartyStreet;
    }

    public String getNotifyPartyCity() {
        return notifyPartyCity;
    }

    public void setNotifyPartyCity(String notifyPartyCity) {
        this.notifyPartyCity = notifyPartyCity;
    }

    public String getNotifyPartyPostcode() {
        return notifyPartyPostcode;
    }

    public void setNotifyPartyPostcode(String notifyPartyPostcode) {
        this.notifyPartyPostcode = notifyPartyPostcode;
    }

    public String getNotifyPartyProvince() {
        return notifyPartyProvince;
    }

    public void setNotifyPartyProvince(String notifyPartyProvince) {
        this.notifyPartyProvince = notifyPartyProvince;
    }

    public String getNotifyPartyCountry() {
        return notifyPartyCountry;
    }

    public void setNotifyPartyCountry(String notifyPartyCountry) {
        this.notifyPartyCountry = notifyPartyCountry;
    }

    public String getNotifyPartyPhoneNo() {
        return notifyPartyPhoneNo;
    }

    public void setNotifyPartyPhoneNo(String notifyPartyPhoneNo) {
        this.notifyPartyPhoneNo = notifyPartyPhoneNo;
    }

    public String getNotifyPartyEmail() {
        return notifyPartyEmail;
    }

    public void setNotifyPartyEmail(String notifyPartyEmail) {
        this.notifyPartyEmail = notifyPartyEmail;
    }

    public String getPaymentInstruction() {
        return paymentInstruction;
    }

    public void setPaymentInstruction(String paymentInstruction) {
        this.paymentInstruction = paymentInstruction;
    }

    @Override
    public String toString() {
        return "CommercialInvoice{" +
                "primaryId=" + primaryId +
                ", notifyPartyName='" + notifyPartyName + '\'' +
                ", notifyPartyBuilding='" + notifyPartyBuilding + '\'' +
                ", notifyPartyStreet='" + notifyPartyStreet + '\'' +
                ", notifyPartyCity='" + notifyPartyCity + '\'' +
                ", notifyPartyPostcode='" + notifyPartyPostcode + '\'' +
                ", notifyPartyProvince='" + notifyPartyProvince + '\'' +
                ", notifyPartyCountry='" + notifyPartyCountry + '\'' +
                ", notifyPartyPhoneNo='" + notifyPartyPhoneNo + '\'' +
                ", notifyPartyEmail='" + notifyPartyEmail + '\'' +
                ", paymentInstruction='" + paymentInstruction + '\'' +
                '}';
    }
}