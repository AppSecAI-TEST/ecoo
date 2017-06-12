package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "transport_type")
public class TransportType extends BaseModel<String> {

    private static final long serialVersionUID = 2060037820403080847L;

    @Id
    @Column(name = "id")
    @Audited
    private String primaryId;

    @Column(name = "descr")
    @Audited
    private String description;

    public TransportType() {
    }

    public TransportType(String primaryId, String description) {
        this.primaryId = primaryId;
        this.description = description;
    }

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public String getPrimaryId() {
        return primaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "transport_type{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}