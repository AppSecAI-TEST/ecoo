package ecoo.data.upload;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("ALL")
public class CsvFile {

    // Map of all the columns in CsvFile. Key is the index of column.
    private SortedMap<Integer, CsvColumn> columnDataMap;

    // Map of all the rows in CSVFile.Key is the index or row.
    private SortedMap<Integer, CsvRow> rowDataMap;
    //
    private RequiredFieldMapping mapping;

    private File file;

    /**
     * constructor for {@link CsvFile} object that represents an entire csv input file. <br>
     * Uploads the source .csv file into the {@link CsvFile} object instance which will be used in
     * the upload process and is easier to manipulate that the actual .csv file.
     *
     * @param inputFile comment
     */
    public CsvFile(File inputFile) throws IOException {
        Assert.notNull(inputFile);
        this.file = inputFile;
        setInputStream0(new FileInputStream(inputFile));
    }

    /**
     * Returns the absolute pathname string of this abstract pathname.
     * <p>
     * <p>
     * If this abstract pathname is already absolute, then the pathname string is simply returned as
     * if by the <code>getPath</code> method. If this abstract pathname is the empty
     * abstract pathname then the pathname string of the current user directory, which is named by
     * the system property <code>user.dir</code>, is returned. Otherwise this pathname is resolved
     * in a system-dependent way. On UNIX systems, a relative pathname is made absolute by resolving
     * it against the current user directory. On Microsoft Windows systems, a relative pathname is
     * made absolute by resolving it against the current directory of the drive named by the
     * pathname, if any; if not, it is resolved against the current user directory.
     *
     * @return The absolute pathname string denoting the same file or directory as this abstract
     * pathname
     * @throws SecurityException If a required system property value cannot be accessed.
     * @see File#isAbsolute()
     */
    public final String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    /**
     * Returns the name of the file or directory denoted by this abstract pathname. This is just the
     * last name in the pathname's name sequence. If the pathname's name sequence is empty, then the
     * empty string is returned.
     *
     * @return The name of the file or directory denoted by this abstract pathname, or the empty
     * string if this pathname's name sequence is empty
     */
    public final String getName() {
        return file.getName();
    }

    /**
     * Used to represents a {@link CsvColumn} in the {@link CsvFile}.
     *
     * @author Abraham T. Moyo
     */
    public class CsvColumn implements Comparable<CsvColumn> {

        private String columnHeading;
        private int columnIndex;

        /**
         * Private constructor, only CSVFile can create CSVColumns.
         */
        private CsvColumn() {
        }

        @Override
        public final int compareTo(CsvColumn o) {
            return Integer.valueOf(getColumnIndex()).compareTo(o.getColumnIndex());
        }

        /**
         * Return the column name.
         *
         * @return The column heading.
         */
        public String getColumnHeading() {
            if (columnHeading == null) {
                if (isFirstRowHeading() && getData(0) != null) {
                    columnHeading = getData(0);
                } else {
                    columnHeading = String.valueOf(getColumnIndex());
                }
            }
            return columnHeading;
        }

        /**
         * Return the data of this column at relevant row index.
         *
         * @param rowIndex comment
         * @return
         */
        @JsonIgnore
        public String getData(int rowIndex) {
            return getData(getLocalRowMap0().get(rowIndex));
        }

        /**
         * Return the data of this column at relevant row index.
         *
         * @param row
         * @return
         */
        @JsonIgnore
        public String getData(CsvRow row) {
            return row.getData(this);
        }

        /**
         * @return the columnIndex
         */
        public final int getColumnIndex() {
            return columnIndex;
        }

        /**
         * @param columnIndex the columnIndex to set
         */
        public final void setColumnIndex(int columnIndex) {
            this.columnIndex = columnIndex;
        }
    }// end CsvColumn

    /**
     * {@link CsvRow} is a row in the csv file. Use either the getData() method in the
     * {@link CsvRow} ,{@link CsvColumn} or {@link CsvFile} to retrieve data from csv file.
     *
     * @author Abraham T. Moyo
     */
    public class CsvRow implements Comparable<CsvRow> {
        // Row index in csv file.
        private int rowIndex;
        private Map<CsvColumn, String> rowDataMap;
        private Map<Integer, String> columns;

        /**
         * Private constructor, only {@link CsvFile} can create {@link CsvRow}.
         */
        private CsvRow() {
            super();
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public final int compareTo(CsvRow o) {
            return Integer.valueOf(getRowIndex()).compareTo(o.getRowIndex());
        }

        /**
         * @return the rowIndex
         */
        public final int getRowIndex() {
            return rowIndex;
        }

        /**
         * Return the cvs file data at relevant CSVColumn in row.
         *
         * @param column
         * @return
         */
        @JsonIgnore
        public String getData(CsvColumn column) {
            return getRowDataMap().get(column);
        }

        /**
         * Return the cvs file data relevant column index.
         *
         * @param columnIndex
         * @return
         */
        @JsonIgnore
        public String getData(int columnIndex) {
            return getData(getLocalColumnsMap0().get(columnIndex));
        }

        /**
         * @return the rowDataMap
         */
        @JsonIgnore
        public final Map<CsvColumn, String> getRowDataMap() {
            if (rowDataMap == null) {
                rowDataMap = new TreeMap<>();
            }
            return rowDataMap;
        }

        public Map<Integer, String> getColumns() {
            if (columns == null) {
                columns = new HashMap<>();
                for (final CsvColumn csvColumn : getRowDataMap().keySet()) {
                    columns.put(csvColumn.getColumnIndex(), getRowDataMap().get(csvColumn));
                }
            }
            return columns;
        }

        /**
         * @param rowIndex the rowIndex to set
         */
        public final void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }
    } // end CsvRow

    /**
     * Set the {@link FileInputStream} used to represent the CsvFile. This will read the file.
     *
     * @param inputStream
     * @throws IOException
     */
    @JsonIgnore
    private void setInputStream0(InputStream inputStream) throws IOException {

        // Reset my columns.
        columnDataMap = null;
        rowDataMap = null;

        CSVReader csvReader = null;
        try {
            // Need to user overloaded construct to pass in "NO_ESCAPE_CHARACTER", other the default
            // CSVReader constructor will create a CSVReader object which will escape all
            // "\" which would result in removing the "\" i.e.: AFORBES\SmithJ will be read as
            // AFORBESSmithJ.
            csvReader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream)),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER);

            List<String[]> csvRows = csvReader.readAll();
            for (int rowIndex = 0, columnIndex = 0; rowIndex < csvRows.size(); rowIndex++, columnIndex = 0) {
                String[] line = csvRows.get(rowIndex);
                CsvRow row = getCsvRow0(rowIndex, true);

                // ensure that first column has default value.
                CsvColumn csvColumn = getCsvColumn0(columnIndex, true);
                row.getRowDataMap().put(csvColumn, "");

                for (String val : line) {
                    csvColumn = getCsvColumn0(columnIndex, true);
                    row.getRowDataMap().put(csvColumn, val);
                    columnIndex++;
                }
            }
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }

    // Return the csv column , if createNew is true , create new csvColumn if none exist.
    private CsvColumn getCsvColumn0(int columnIndex, boolean createNew) {
        CsvColumn csvColumn = getLocalColumnsMap0().get(columnIndex);
        if (csvColumn == null && createNew) {
            csvColumn = new CsvColumn();
            csvColumn.setColumnIndex(columnIndex);
            getLocalColumnsMap0().put(columnIndex, csvColumn);
        }

        return csvColumn;
    }

    // Return the csv row,if createNew is true, create new csvRow if no exist.
    private CsvRow getCsvRow0(int rowIndex, boolean createNew) {
        CsvRow csvRow = getLocalRowMap0().get(rowIndex);
        if (csvRow == null && createNew) {
            csvRow = new CsvRow();
            csvRow.setRowIndex(rowIndex);
            getLocalRowMap0().put(rowIndex, csvRow);
        }
        return csvRow;
    }

    // Return set of CsvColumn that is the csv files column.
    private SortedMap<Integer, CsvColumn> getLocalColumnsMap0() {
        if (columnDataMap == null) {
            columnDataMap = new TreeMap<>();
        }
        return columnDataMap;
    }

    // Return set of CSVRows that is the csv files rows.
    private SortedMap<Integer, CsvRow> getLocalRowMap0() {
        if (rowDataMap == null) {
            rowDataMap = new TreeMap<>();
        }
        return rowDataMap;
    }

    /**
     * Return whether the first row in file is the column headings.
     *
     * @return
     */
    @JsonIgnore
    public boolean isFirstRowHeading() {
        if (mapping == null) {
            return false;
        }
        return mapping.isFirstRowHeading();
    }

    /**
     * Return a map of all the rows in csv file.
     *
     * @return
     */
    @JsonIgnore
    public SortedMap<Integer, CsvRow> getRowsMap() {
        return new TreeMap<>(getLocalRowMap0());
    }

    /**
     * Return a map of all the {@link CsvColumn} in {@link CsvFile}. The key to map is index of
     * csvColumn in file.
     *
     * @return
     */
    public SortedMap<Integer, CsvColumn> getColumnsMap() {
        return new TreeMap<>(getLocalColumnsMap0());
    }

    /**
     * Return the {@link CsvColumn} at given columnIndex.
     *
     * @param columnIndex
     * @return
     */
    @JsonIgnore
    public CsvColumn getCsvColumnBy(Integer columnIndex) {
        return getLocalColumnsMap0().get(columnIndex);
    }

    /**
     * Return whether this csv file has any data.
     *
     * @return
     */
    @JsonIgnore
    public boolean containData() {
        return getLocalRowMap0().size() > 0;
    }

    @Override
    public String toString() {
        return getColumnsMap().values().toString();
    }

    public final void setMapping(RequiredFieldMapping mapping) {
        this.mapping = mapping;
    }

}
