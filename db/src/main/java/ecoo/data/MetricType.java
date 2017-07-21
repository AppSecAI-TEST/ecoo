package ecoo.data;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "metric_type")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class MetricType extends BaseModel<String> {

    private static final long serialVersionUID = 7355245443793182419L;

    public enum Type {
        ShipmentCountCurrentMonth("SCM"),ShipmentCountLastMonth("SLM"),OpenShipmentCount("OSC");

        private String primaryId;

        Type(String primaryId) {
            this.primaryId = primaryId;
        }

        public String getPrimaryId() {
            return primaryId;
        }

        public static Type getTypeByPrimaryId(String primaryId) {
            for (Type type : Type.values()) {
                if (type.getPrimaryId().equalsIgnoreCase(primaryId)) {
                    return type;
                }
            }
            return null;
        }
    }

    @Id
    @Column(name = "id")
    private String primaryId;

    @Column(name = "name")
    private String name;

    @Column(name = "descr")
    private String description;

    public MetricType() {
    }

    public MetricType(String primaryId, String name, String description) {
        this.primaryId = primaryId;
        this.name = name;
        this.description = description;
    }

    @Override
    public String getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MetricType{" +
                "primaryId=" + primaryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
