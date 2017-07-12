package ecoo.data;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "amount_type")
public class AmountType extends BaseModel<Integer> {

    private static final long serialVersionUID = -849125304465931221L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "code")
    private String code;

    @Column(name = "descr")
    private String description;

    @Column(name = "amount_schema")
    private String schema;

    /**
     * Constructs a new {@link AmountType} model object.
     */
    public AmountType() {
    }

    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", schema='" + schema + '\'' +
                '}';
    }
}
