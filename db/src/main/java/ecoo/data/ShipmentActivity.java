package ecoo.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "shipment_activity")
public class ShipmentActivity extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 3655032359946659208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "descr")
    private String descr;

    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return "ShipmentActivity{" +
                "primaryId=" + primaryId +
                ", groupId=" + groupId +
                ", descr='" + descr + '\'' +
                '}';
    }
}