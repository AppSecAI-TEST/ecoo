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
@Document(type = "ecoo", indexName = "ecoo.shipment", shards = 1, replicas = 0)
public class Shipment extends BaseModel<Integer> implements Serializable {

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

    @Column(name = "consignee_name")
    @Audited
    private String consigneeName;

    @Column(name = "consignee_line1")
    @Audited
    private String consigneeLine1;

    @Column(name = "consignee_line2")
    @Audited
    private String consigneeLine2;

    @Column(name = "consignee_line3")
    @Audited
    private String consigneeLine3;

    @Column(name = "consignee_line4")
    @Audited
    private String consigneeLine4;

    @Column(name = "buyer_name")
    @Audited
    private String buyerName;

    @Column(name = "buyer_line1")
    @Audited
    private String buyerLine1;

    @Column(name = "buyer_line2")
    @Audited
    private String buyerLine2;

    @Column(name = "buyer_line3")
    @Audited
    private String buyerLine3;

    @Column(name = "buyer_line4")
    @Audited
    private String buyerLine4;

    @Column(name = "place_of_issue")
    @Audited
    private String placeOfIssue;

    @Column(name = "date_of_issue")
    @Audited
    private Date dateOfIssue;

    @Column(name = "status")
    @Audited
    private String status;

    @JsonIgnore
    public boolean isInStatus(ShipmentStatus status) {
        Assert.notNull(status);
        return status.id().equalsIgnoreCase(this.status);
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeLine1() {
        return consigneeLine1;
    }

    public void setConsigneeLine1(String consigneeLine1) {
        this.consigneeLine1 = consigneeLine1;
    }

    public String getConsigneeLine2() {
        return consigneeLine2;
    }

    public void setConsigneeLine2(String consigneeLine2) {
        this.consigneeLine2 = consigneeLine2;
    }

    public String getConsigneeLine3() {
        return consigneeLine3;
    }

    public void setConsigneeLine3(String consigneeLine3) {
        this.consigneeLine3 = consigneeLine3;
    }

    public String getConsigneeLine4() {
        return consigneeLine4;
    }

    public void setConsigneeLine4(String consigneeLine4) {
        this.consigneeLine4 = consigneeLine4;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerLine1() {
        return buyerLine1;
    }

    public void setBuyerLine1(String buyerLine1) {
        this.buyerLine1 = buyerLine1;
    }

    public String getBuyerLine2() {
        return buyerLine2;
    }

    public void setBuyerLine2(String buyerLine2) {
        this.buyerLine2 = buyerLine2;
    }

    public String getBuyerLine3() {
        return buyerLine3;
    }

    public void setBuyerLine3(String buyerLine3) {
        this.buyerLine3 = buyerLine3;
    }

    public String getBuyerLine4() {
        return buyerLine4;
    }

    public void setBuyerLine4(String buyerLine4) {
        this.buyerLine4 = buyerLine4;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}