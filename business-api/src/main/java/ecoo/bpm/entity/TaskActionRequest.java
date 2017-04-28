package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskActionRequest {

    private String processInstanceId;

    private Integer requestingUserId;

    private boolean approve;

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

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
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
                ", approve=" + approve +
                ", comment='" + comment + '\'' +
                '}';
    }
}