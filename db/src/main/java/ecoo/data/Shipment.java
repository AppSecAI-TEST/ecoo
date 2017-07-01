package ecoo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "shipment")
@Document(type = "ecoo.shipment", indexName = "ecoo.shipment", shards = 1, replicas = 0)
public class Shipment extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 5624075215737017054L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "chamber_id")
    @Audited
    private Integer chamberId;

    @Column(name = "export_ref")
    @Audited
    private String exporterReference;

    @Column(name = "export_inv_no")
    @Audited
    private String exportInvoiceNumber;

    @Column(name = "export_inv_date")
    @Audited
    private Date exportInvoiceDate;

    @Column(name = "buyer_ref")
    @Audited
    private String buyerReference;

    @Column(name = "buyer_order_date")
    @Audited
    private Date buyerOrderDate;

    @Column(name = "letter_credit_no")
    @Audited
    private String letterOfCreditNumber;

    @Column(name = "ucr_no")
    @Audited
    private String ucrNumber;

    @Column(name = "exporter_name")
    @Audited
    private String exporterName;

    @Column(name = "exporter_building")
    @Audited
    private String exporterBuilding;

    @Column(name = "exporter_street")
    @Audited
    private String exporterStreet;

    @Column(name = "exporter_city")
    @Audited
    private String exporterCity;

    @Column(name = "exporter_postcode")
    @Audited
    private String exporterPostcode;

    @Column(name = "exporter_province")
    @Audited
    private String exporterProvince;

    @Column(name = "exporter_country")
    @Audited
    private String exporterCountry;

    @Column(name = "consignee_name")
    @Audited
    private String consigneeName;

    @Column(name = "consignee_building")
    @Audited
    private String consigneeBuilding;

    @Column(name = "consignee_street")
    @Audited
    private String consigneeStreet;

    @Column(name = "consignee_city")
    @Audited
    private String consigneeCity;

    @Column(name = "consignee_postcode")
    @Audited
    private String consigneePostcode;

    @Column(name = "consignee_province")
    @Audited
    private String consigneeProvince;

    @Column(name = "consignee_country")
    @Audited
    private String consigneeCountry;

    @Column(name = "buyer_name")
    @Audited
    private String buyerName;

    @Column(name = "buyer_building")
    @Audited
    private String buyerBuilding;

    @Column(name = "buyer_street")
    @Audited
    private String buyerStreet;

    @Column(name = "buyer_city")
    @Audited
    private String buyerCity;

    @Column(name = "buyer_postcode")
    @Audited
    private String buyerPostcode;

    @Column(name = "buyer_province")
    @Audited
    private String buyerProvince;

    @Column(name = "buyer_country")
    @Audited
    private String buyerCountry;

    @Column(name = "place_of_issue")
    @Audited
    private String placeOfIssue;

    @Column(name = "date_of_issue")
    @Audited
    private Date dateOfIssue;

    @Column(name = "date_submitted")
    @Audited
    private Date dateSubmitted;

    @Column(name = "status")
    @Audited
    private String status;

    @Column(name = "owner")
    @Audited
    private Integer ownerId;

    @Column(name = "process_instance_id")
    @Audited
    private String processInstanceId;

    @Column(name = "transport_type")
    @Audited
    private String transportTypeId;

    @Column(name = "port_of_load")
    @Audited
    private String portOfLoading;

    @Column(name = "port_of_accept")
    @Audited
    private String portOfAcceptance;

    @Column(name = "currency")
    @Audited
    private String currency;

    @JsonIgnore
    public boolean isInStatus(ShipmentStatus... status) {
        Assert.notNull(status);
        for (ShipmentStatus s : status) {
            if (s.id().equalsIgnoreCase(this.status)) {
                return true;
            }
        }
        return false;
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

    public Integer getChamberId() {
        return chamberId;
    }

    public void setChamberId(Integer chamberId) {
        this.chamberId = chamberId;
    }

    public String getExporterReference() {
        return exporterReference;
    }

    public void setExporterReference(String exporterReference) {
        this.exporterReference = exporterReference;
    }

    public String getExportInvoiceNumber() {
        return exportInvoiceNumber;
    }

    public void setExportInvoiceNumber(String exportInvoiceNumber) {
        this.exportInvoiceNumber = exportInvoiceNumber;
    }

    public Date getExportInvoiceDate() {
        return exportInvoiceDate;
    }

    public void setExportInvoiceDate(Date exportInvoiceDate) {
        this.exportInvoiceDate = exportInvoiceDate;
    }

    public String getBuyerReference() {
        return buyerReference;
    }

    public void setBuyerReference(String buyerReference) {
        this.buyerReference = buyerReference;
    }

    public Date getBuyerOrderDate() {
        return buyerOrderDate;
    }

    public void setBuyerOrderDate(Date buyerOrderDate) {
        this.buyerOrderDate = buyerOrderDate;
    }

    public String getLetterOfCreditNumber() {
        return letterOfCreditNumber;
    }

    public void setLetterOfCreditNumber(String letterOfCreditNumber) {
        this.letterOfCreditNumber = letterOfCreditNumber;
    }

    public String getUcrNumber() {
        return ucrNumber;
    }

    public void setUcrNumber(String ucrNumber) {
        this.ucrNumber = ucrNumber;
    }

    public String getExporterName() {
        return exporterName;
    }

    public void setExporterName(String exporterName) {
        this.exporterName = exporterName;
    }

    public String getExporterBuilding() {
        return exporterBuilding;
    }

    public void setExporterBuilding(String exporterBuilding) {
        this.exporterBuilding = exporterBuilding;
    }

    public String getExporterStreet() {
        return exporterStreet;
    }

    public void setExporterStreet(String exporterStreet) {
        this.exporterStreet = exporterStreet;
    }

    public String getExporterCity() {
        return exporterCity;
    }

    public void setExporterCity(String exporterCity) {
        this.exporterCity = exporterCity;
    }

    public String getExporterPostcode() {
        return exporterPostcode;
    }

    public void setExporterPostcode(String exporterPostcode) {
        this.exporterPostcode = exporterPostcode;
    }

    public String getExporterProvince() {
        return exporterProvince;
    }

    public void setExporterProvince(String exporterProvince) {
        this.exporterProvince = exporterProvince;
    }

    public String getExporterCountry() {
        return exporterCountry;
    }

    public void setExporterCountry(String exporterCountry) {
        this.exporterCountry = exporterCountry;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeBuilding() {
        return consigneeBuilding;
    }

    public void setConsigneeBuilding(String consigneeBuilding) {
        this.consigneeBuilding = consigneeBuilding;
    }

    public String getConsigneeStreet() {
        return consigneeStreet;
    }

    public void setConsigneeStreet(String consigneeStreet) {
        this.consigneeStreet = consigneeStreet;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public String getConsigneePostcode() {
        return consigneePostcode;
    }

    public void setConsigneePostcode(String consigneePostcode) {
        this.consigneePostcode = consigneePostcode;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerBuilding() {
        return buyerBuilding;
    }

    public void setBuyerBuilding(String buyerBuilding) {
        this.buyerBuilding = buyerBuilding;
    }

    public String getBuyerStreet() {
        return buyerStreet;
    }

    public void setBuyerStreet(String buyerStreet) {
        this.buyerStreet = buyerStreet;
    }

    public String getBuyerCity() {
        return buyerCity;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerPostcode() {
        return buyerPostcode;
    }

    public void setBuyerPostcode(String buyerPostcode) {
        this.buyerPostcode = buyerPostcode;
    }

    public String getBuyerProvince() {
        return buyerProvince;
    }

    public void setBuyerProvince(String buyerProvince) {
        this.buyerProvince = buyerProvince;
    }

    public String getBuyerCountry() {
        return buyerCountry;
    }

    public void setBuyerCountry(String buyerCountry) {
        this.buyerCountry = buyerCountry;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(String transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfAcceptance() {
        return portOfAcceptance;
    }

    public void setPortOfAcceptance(String portOfAcceptance) {
        this.portOfAcceptance = portOfAcceptance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "primaryId=" + primaryId +
                ", chamberId=" + chamberId +
                ", exporterReference='" + exporterReference + '\'' +
                ", exportInvoiceNumber='" + exportInvoiceNumber + '\'' +
                ", exportInvoiceDate=" + exportInvoiceDate +
                ", buyerReference='" + buyerReference + '\'' +
                ", buyerOrderDate=" + buyerOrderDate +
                ", letterOfCreditNumber='" + letterOfCreditNumber + '\'' +
                ", ucrNumber='" + ucrNumber + '\'' +
                ", exporterName='" + exporterName + '\'' +
                ", exporterBuilding='" + exporterBuilding + '\'' +
                ", exporterStreet='" + exporterStreet + '\'' +
                ", exporterCity='" + exporterCity + '\'' +
                ", exporterPostcode='" + exporterPostcode + '\'' +
                ", exporterProvince='" + exporterProvince + '\'' +
                ", exporterCountry='" + exporterCountry + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", consigneeBuilding='" + consigneeBuilding + '\'' +
                ", consigneeStreet='" + consigneeStreet + '\'' +
                ", consigneeCity='" + consigneeCity + '\'' +
                ", consigneePostcode='" + consigneePostcode + '\'' +
                ", consigneeProvince='" + consigneeProvince + '\'' +
                ", consigneeCountry='" + consigneeCountry + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", buyerBuilding='" + buyerBuilding + '\'' +
                ", buyerStreet='" + buyerStreet + '\'' +
                ", buyerCity='" + buyerCity + '\'' +
                ", buyerPostcode='" + buyerPostcode + '\'' +
                ", buyerProvince='" + buyerProvince + '\'' +
                ", buyerCountry='" + buyerCountry + '\'' +
                ", placeOfIssue='" + placeOfIssue + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                ", dateSubmitted=" + dateSubmitted +
                ", status='" + status + '\'' +
                ", ownerId=" + ownerId +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", transportTypeId='" + transportTypeId + '\'' +
                ", portOfLoading='" + portOfLoading + '\'' +
                ", portOfAcceptance='" + portOfAcceptance + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}