package ecoo.data;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "amount_type")
public class AmountType extends BaseModel<String> {

    private static final long serialVersionUID = -849125304465931221L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String primaryId;

    @Column(name = "descr")
    private String description;

    @Column(name = "amount_schema")
    private String schema;

    /**
     * Constructs a new {@link AmountType} model object.
     */
    public AmountType() {
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public final String getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "AmountType{" +
                "primaryId='" + primaryId + '\'' +
                ", description='" + description + '\'' +
                ", schema='" + schema + '\'' +
                '}';
    }
}
