package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "doc_type")
public class DocumentType extends BaseModel<String> {

    private static final long serialVersionUID = 558790961340697756L;

    @Id
    @Column(name = "id")
    @Audited
    private String primaryId;

    @Column(name = "descr")
    @Audited
    private String description;

    public DocumentType() {
    }

    public DocumentType(String primaryId, String description) {
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
        return "DocumentType{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}