package ecoo.data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "endpoint")
public class Endpoint extends BaseModel<Integer> {

    private static final long serialVersionUID = 3974930375289084104L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "requested_time")
    private Date requestedTime;

    @Column(name = "response")
    private String response;

    @Column(name = "status")
    private String status;

    @Override
    public Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "primaryId=" + primaryId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", requestedTime=" + requestedTime +
                ", status='" + status + '\'' +
                '}';
    }
}