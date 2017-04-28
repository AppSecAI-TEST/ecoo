package ecoo.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since Sepetember 2016
 */

@Entity
@Table(name = "title")
public class Title extends BaseModel<String> {

    private static final long serialVersionUID = -6052051632817263810L;

    @Id
    @Column(name = "id")
    private String primaryId;

    @Column(name = "descr")
    private String description;

    @Override
    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}