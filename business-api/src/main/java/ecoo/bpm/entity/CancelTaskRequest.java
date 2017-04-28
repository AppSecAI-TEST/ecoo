package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since December 2016
 */
public class CancelTaskRequest {

    private String processInstanceId;

    private Integer requestingUserId;

    public CancelTaskRequest() {
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getRequestingUserId() {
        return requestingUserId;
    }

    public void setRequestingUserId(Integer requestingUserId) {
        this.requestingUserId = requestingUserId;
    }

    @Override
    public String toString() {
        return "CancelTaskRequest{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", requestingUserId=" + requestingUserId +
                '}';
    }
}