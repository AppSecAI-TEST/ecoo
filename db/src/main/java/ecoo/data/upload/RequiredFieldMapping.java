package ecoo.data.upload;

import ecoo.data.BaseModel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "upload_map")
public class RequiredFieldMapping extends BaseModel<Integer> {

    private static final long serialVersionUID = -44000713992596397L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "upload_type_id")
    private String uploadTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "has_heading")
    private boolean firstRowHeading;

    @OneToMany(mappedBy = "mapping", fetch = FetchType.LAZY)
//    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
//            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<RequiredFieldMappingItem> mappingItems;

    @Override
    public Integer getPrimaryId() {
        return primaryId;
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

    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the mappingDetails
     */
    public final Collection<RequiredFieldMappingItem> getMappingItems() {
        return mappingItems;
    }

    /**
     * @param mappingItems the mappingDetails to set
     */
    public final void setMappingItems(Collection<RequiredFieldMappingItem> mappingItems) {
        if (mappingItems != null) {
            for (RequiredFieldMappingItem item : mappingItems) {
                item.setMapping(this);
            }
        }
        this.mappingItems = mappingItems;
    }

    public final String getUploadTypeId() {
        return uploadTypeId;
    }

    public final void setUploadTypeId(String uploadTypeId) {
        this.uploadTypeId = uploadTypeId;
    }

    public final boolean isFirstRowHeading() {
        return firstRowHeading;
    }

    public final void setFirstRowHeading(boolean firstRowHeading) {
        this.firstRowHeading = firstRowHeading;
    }
}
