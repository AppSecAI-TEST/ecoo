package ecoo.ws.upload.json;

import ecoo.data.upload.UploadData;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class QueryUploadDataResponse {

    private int iTotalRecords;

    private int iTotalDisplayRecords;

    private Collection<UploadData> aaData;

    public QueryUploadDataResponse(int iTotalRecords, int iTotalDisplayRecords, Collection<UploadData> aaData) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.aaData = aaData;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Collection<UploadData> getAaData() {
        return aaData;
    }

    public void setAaData(Collection<UploadData> aaData) {
        this.aaData = aaData;
    }
}
