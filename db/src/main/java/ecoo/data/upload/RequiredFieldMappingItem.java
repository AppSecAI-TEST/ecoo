package ecoo.data.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ecoo.data.BaseModel;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "upload_map_detail")
public class RequiredFieldMappingItem extends BaseModel<Integer> {

	private static final long serialVersionUID = -4890866966890028405L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer primaryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "map_id", nullable = false)
	private RequiredFieldMapping mapping;

	@Column(name = "csv_column_index")
	private Integer csvColumnIndex;

	@Column(name = "table_column_name")
	private String tableColumnName;

	/**
	 * Constructs a new {@link RequiredFieldMappingItem} model object.
	 */
	public RequiredFieldMappingItem() {}

	/**
	 * @return the primaryId
	 */
	@Override
	public final Integer getPrimaryId() {
		return primaryId;
	}

	/**
	 * @return the csvColumnIndex
	 */
	public final Integer getCsvColumnIndex() {
		return csvColumnIndex;
	}

	/**
	 * @param csvColumnIndex the csvColumnIndex to set
	 */
	public final void setCsvColumnIndex(Integer csvColumnIndex) {
		this.csvColumnIndex = csvColumnIndex;
	}

	/**
	 * @return the tableColumnKey
	 */
	public final String getTableColumnName() {
		return tableColumnName;
	}

	/**
	 * @param tableColumnName the tableColumnKey to set
	 */
	public final void setTableColumnName(String tableColumnName) {
		this.tableColumnName = tableColumnName;
	}

	/**
	 * @param primaryId the primaryId to set
	 */
	@Override
	public final void setPrimaryId(Integer primaryId) {
		this.primaryId = primaryId;
	}

	/**
	 * @return the mapping
	 */
	@JsonIgnore
	public final RequiredFieldMapping getMapping() {
		return mapping;
	}

	/**
	 * @param mapping the mapping to set
	 */
	@JsonIgnore
	public final void setMapping(RequiredFieldMapping mapping) {
		this.mapping = mapping;
	}

}
