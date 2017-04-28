package ecoo.data;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Audited
public class Province extends BaseModel<Integer> {

    private static final long serialVersionUID = -7510701126808705359L;

    @Id
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "descr")
    private String description;

    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(Integer primaryId) {
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
        return "Province{" +
                "primaryId=" + primaryId +
                ", description='" + description + '\'' +
                '}';
    }
}
