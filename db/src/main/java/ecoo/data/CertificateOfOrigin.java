package ecoo.data;

import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Entity
@Table(name = "doc_coo")
@Document(type = "ecoo.coo", indexName = "ecoo.coo", shards = 1, replicas = 0)
public class CertificateOfOrigin extends BaseModel<Integer> implements Serializable {

    private static final long serialVersionUID = 4991785189692239969L;

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "shipment_id")
    @Audited
    private Integer primaryId;

    @Column(name = "remarks")
    @Audited
    private String remarks;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private List<CertificateOfOriginLine> lines = new ArrayList<>();


    /**
     * Returns the unique identifier of the object.
     *
     * @return The primary key value.
     */
    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    /**
     * Set the unique identifier for the object.
     *
     * @param primaryId The primary key to set.
     */
    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<CertificateOfOriginLine> getLines() {
        return lines;
    }

    public void setLines(List<CertificateOfOriginLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "CertificateOfOrigin{" +
                "primaryId=" + primaryId +
                ", remarks='" + remarks + '\'' +
                ", lines=" + lines +
                '}';
    }
}
