package ecoo.ws.common.json;


/**
 * @author Justin Rundle
 * @since April 2017
 */
public class DataQueryResponse {

    private Object object;

    private String image;

    public DataQueryResponse(Object object, String image) {
        this.object = object;
        this.image = "styles/img/icons/" + image;
    }

    public Object getObject() {
        return object;
    }

    public String getImage() {
        return image;
    }
}
