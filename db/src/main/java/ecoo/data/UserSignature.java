
package ecoo.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.envers.Audited;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "user_signature")
public class UserSignature extends BaseModel<Integer> {

    private static final long serialVersionUID = 6237292550334392411L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Audited
    private Integer primaryId;

    @Column(name = "user_id")
    @Audited
    private Integer userId;

    @Column(name = "encoded_image")
    @Audited
    private String encodedImage;

    @Column(name = "eff_from_date")
    @Audited
    private Date effectiveFrom;

    @Column(name = "eff_to_date")
    @Audited
    private Date effectiveTo;


    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

    public void setEncodedImage(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @JsonGetter
    public boolean isActive() {
        final DateTime now = DateTime.now();
        return now.isAfter(DateTime.now().withMillis(effectiveFrom.getTime()).withTimeAtStartOfDay())
                || now.isBefore(DateTime.now().withMillis(effectiveTo.getTime()).withTimeAtStartOfDay());
    }

    @Override
    public String toString() {
        return "UserSignature{" +
                "primaryId=" + primaryId +
                ", userId=" + userId +
                ", encodedImage='" + encodedImage + '\'' +
                ", effectiveFrom=" + effectiveFrom +
                ", effectiveTo='" + effectiveTo + '\'' +
                '}';
    }
}