package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since December 2016
 */
public class CancelTaskResponse {

    private final String message;

    private final WorkflowRequest workflowRequest;

    @JsonCreator
    public CancelTaskResponse(@JsonProperty("message") String message
            , @JsonProperty("workflowRequest") WorkflowRequest workflowRequest) {
        this.message = message;
        this.workflowRequest = workflowRequest;
    }

    public String getMessage() {
        return message;
    }

    public WorkflowRequest getWorkflowRequest() {
        return workflowRequest;
    }

    @Override
    public String toString() {
        return "CancelTaskResponse{" +
                "message='" + message + '\'' +
                "workflowRequest=" + workflowRequest +
                '}';
    }
}