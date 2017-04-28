package ecoo.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "company_type")
public class CompanyType extends BaseModel<String> implements Serializable {

    private static final long serialVersionUID = -2128808144443271533L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String primaryId;

    @Column(name = "descr")
    private String description;

    public CompanyType() {
    }

    public CompanyType(String primaryId, String description) {
        this.primaryId = primaryId;
        this.description = description;
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

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
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
        return "CompanyType{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}