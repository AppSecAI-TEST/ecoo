package ecoo.data;

import org.hibernate.annotations.Cascade;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "shipment_activity_grp")
@Document(type = "ecoo.shipment.activity.grp", indexName = "ecoo.shipment.activity.grp", shards = 1, replicas = 0)
public class ShipmentActivityGroup extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 3655032359946659208L;

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "shipment_id")
    private Integer shipmentId;

    @Column(name = "date_modified")
    private Date dateModified;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private List<ShipmentActivity> activities = new ArrayList<>();

    public ShipmentActivityGroup(Integer userId, String displayName, Integer shipmentId, Date dateModified) {
        this.userId = userId;
        this.displayName = displayName;
        this.shipmentId = shipmentId;
        this.dateModified = dateModified;
    }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public List<ShipmentActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ShipmentActivity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "ShipmentActivityGroup{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
                ", displayName='" + displayName + '\'' +
                ", shipmentId=" + shipmentId +
                ", dateModified=" + dateModified +
                '}';
    }
}