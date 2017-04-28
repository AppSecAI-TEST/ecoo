package ecoo.data.upload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PreviewCsvFileRow {

    private Integer columnIndex;

    private String value;

    public PreviewCsvFileRow() {
    }

    @JsonCreator
    public PreviewCsvFileRow(@JsonProperty("columnIndex") Integer columnIndex
            , @JsonProperty("value") String value) {
        this.columnIndex = columnIndex;
        this.value = value;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PreviewCsvFileRow{" +
                "columnIndex=" + columnIndex +
                ", value='" + value + '\'' +
                '}';
    }
}
