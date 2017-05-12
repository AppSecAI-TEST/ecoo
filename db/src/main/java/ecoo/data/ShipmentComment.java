package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.builder.TimeDescriptionBuilder;
import org.joda.time.DateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since Mary 2017
 */
@Entity
@Table(name = "shipment_comment")
@Document(type = "ecoo", indexName = "ecoo.shipment.comment", shards = 1, replicas = 0)
public class ShipmentComment extends BaseModel<Integer> implements Serializable {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "shipment_id")
    private Integer shipmentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "text")
    private String text;

    @Column(name = "date_created")
    private Date dateCreated;

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

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonGetter
    public String getDateCreatedDescription() {
        if (this.dateCreated == null) return null;
        return TimeDescriptionBuilder.aTimeDescription()
                .withStartTime(this.dateCreated)
                .witEvaluationDate(DateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return "ShipmentComment{" +
                "primaryId=" + primaryId +
                ", shipmentId=" + shipmentId +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}