package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskCompletedResponseBuilder {
    private String processInstanceId;
    private String taskId;
    private String taskName;
    private String taskDefinitionKey;
    private String taskAssignee;

    private TaskCompletedResponseBuilder() {
    }

    public static TaskCompletedResponseBuilder aTaskCompletedResponse() {
        return new TaskCompletedResponseBuilder();
    }

    public TaskCompletedResponseBuilder withProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public TaskCompletedResponseBuilder withTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public TaskCompletedResponseBuilder withTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public TaskCompletedResponseBuilder withTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public TaskCompletedResponseBuilder withTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
        return this;
    }

    public TaskCompletedResponse build() {
        return new TaskCompletedResponse(processInstanceId, taskId, taskName, taskDefinitionKey, taskAssignee);
    }
}
