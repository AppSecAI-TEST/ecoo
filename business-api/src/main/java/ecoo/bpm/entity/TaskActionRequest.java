package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskActionRequest {

    private String processInstanceId;

    private Integer requestingUserId;

    private String action;

    private String comment;

    public TaskActionRequest() {
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "TaskActionRequest{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", requestingUserId=" + requestingUserId +
                ", action='" + action + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}