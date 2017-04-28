package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskCompletedResponse {

    private String processInstanceId;

    private String taskId;

    private String taskName;

    private String taskDefinitionKey;

    private String taskAssignee;

    @JsonCreator
    public TaskCompletedResponse(@JsonProperty("processInstanceId") String processInstanceId
            ,@JsonProperty("taskId")  String taskId
            ,@JsonProperty("taskName")  String taskName
            ,@JsonProperty("taskDefinitionKey")  String taskDefinitionKey
            ,@JsonProperty("taskAssignee")  String taskAssignee) {
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDefinitionKey = taskDefinitionKey;
        this.taskAssignee = taskAssignee;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    @Override
    public String toString() {
        return "TaskCompletedResponse{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", taskAssignee='" + taskAssignee + '\'' +
                '}';
    }
}