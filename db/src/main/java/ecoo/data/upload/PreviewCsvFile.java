package ecoo.data.upload;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PreviewCsvFile {

    private String fileName;

    private String absolutePath;

    private Collection<Integer> columns;

    private Map<Integer, String> firstRow;

    private Map<Integer, List<PreviewCsvFileRow>> rows;


    public PreviewCsvFile() {
    }

    public PreviewCsvFile(String fileName, String absolutePath, Collection<Integer> columns, Map<Integer, String> firstRow
            , Map<Integer, List<PreviewCsvFileRow>> rows) {
        this.fileName = fileName;
        this.absolutePath = absolutePath;
        this.columns = columns;
        this.firstRow = firstRow;
        this.rows = rows;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Collection<Integer> getColumns() {
        return columns;
    }

    public void setColumns(Collection<Integer> columns) {
        this.columns = columns;
    }

    public Map<Integer, String> getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(Map<Integer, String> firstRow) {
        this.firstRow = firstRow;
    }

    public Map<Integer, List<PreviewCsvFileRow>> getRows() {
        return rows;
    }

    public void setRows(Map<Integer, List<PreviewCsvFileRow>> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PreviewCsvFile{" +
                "fileName='" + fileName + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", columns=" + columns +
                ", firstRow=" + firstRow +
                ", rows=" + rows +
                '}';
    }
}
