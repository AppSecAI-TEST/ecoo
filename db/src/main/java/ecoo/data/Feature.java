package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since August 2016
 */
@Entity
@Table(name = "feature")
public final class Feature extends BaseModel<Integer> {

    private static final long serialVersionUID = 7272335525501016490L;

    public enum Type {
        APP_HOME, SMTP_SERVER, SMTP_USERNAME, SMTP_PWD, SMTP_DEBUG, SMTP_PORT, NON_PRODUCTION_EMAIL, OUTGOING_EMAIL
        , OUTGOING_DISPLAY_NAME, GATEWAY_URL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "name")
    @Audited
    private String name;

    @Column(name = "value")
    @Audited
    private String value;

    @Column(name = "descr")
    @Audited
    private String description;

    /**
     * Constructs a new {@link Feature} model object.
     */
    public Feature() {
    }

    public Feature(final String value, final String description) {
        this.value = value;
        this.description = description;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#getPrimaryId()
     */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see ecoo.data.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public final String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public final void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }
}