package ecoo.data;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "captcha")
public class Captcha extends BaseModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "value")
    private String value;

    @Column(name = "data")
    private String data;

    /**
     * Constructs a new {@link Captcha} model object.
     */
    public Captcha() {
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "primaryId=" + primaryId +
                ", value='" + value + '\'' +
                '}';
    }
}
