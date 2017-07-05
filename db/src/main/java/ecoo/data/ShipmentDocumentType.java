package ecoo.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "shipment_doc_type")
public class ShipmentDocumentType extends BaseModel<String> {

    private static final long serialVersionUID = -7651217826312489918L;


    public enum Type {
        CommercialInvoice("CI"), ElectronicCommercialInvoice("ECI"), ElectronicCertificateOfOrigin("ECO");

        private String id;

        Type(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }

    @Id
    @Column(name = "id")
    private String primaryId;

    @Column(name = "descr")
    private String description;

    public ShipmentDocumentType() {
    }

    public ShipmentDocumentType(String primaryId, String description) {
        this.primaryId = primaryId;
        this.description = description;
    }

    @Override
    public String getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ShipmentDocumentType{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}