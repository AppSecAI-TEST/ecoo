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
 * @since July 2017
 */
//@Entity
//@Table(name = "shipment_activity")
//@Document(type = "ecoo.shipment.activity", indexName = "ecoo.shipment.activity", shards = 1, replicas = 0)
public class ShipmentActivity extends BaseModel<Integer> implements Serializable {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "shipment_id")
    private Integer shipmentId;

    @Column(name = "descr")
    private String descr;

    @Column(name = "date_modified")
    private Date dateModified;


    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(Integer primaryId) {
this.primaryId = primaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return "ShipmentActivity{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
                ", shipmentId=" + shipmentId +
                ", descr='" + descr + '\'' +
                ", dateModified=" + dateModified +
                '}';
    }
}