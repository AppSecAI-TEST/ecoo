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
@Table(name = "comm_type")
public class CommunicationType extends BaseModel<String> {

    private static final long serialVersionUID = -7651217826312489918L;

    @Id
    @Column(name = "id")
    private String primaryId;

    @Column(name = "descr")
    private String description;

    public CommunicationType() {
    }

    public CommunicationType(String primaryId, String description) {
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
        return "CommunicationType{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}