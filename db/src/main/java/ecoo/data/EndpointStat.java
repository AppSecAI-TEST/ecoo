package ecoo.data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "endpoint_stat")
public class EndpointStat extends BaseModel<Integer> {

    private static final long serialVersionUID = 1364888976844595895L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "endpoint_id")
    private Integer apiId;

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

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
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
        return "EndpointStat{" +
                "primaryId=" + primaryId +
                ", apiId=" + apiId +
                ", requestedTime=" + requestedTime +
                ", status='" + status + '\'' +
                '}';
    }
}