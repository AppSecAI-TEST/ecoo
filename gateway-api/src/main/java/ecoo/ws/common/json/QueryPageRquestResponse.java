package ecoo.ws.common.json;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class QueryPageRquestResponse {

    private int iTotalRecords;

    private int iTotalDisplayRecords;

    private List<?> aaData;

    public QueryPageRquestResponse(int iTotalRecords, int iTotalDisplayRecords, List<?> aaData) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.aaData = aaData;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public List<?> getAaData() {
        return aaData;
    }
}
