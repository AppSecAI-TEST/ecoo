package ecoo.ws.common.json;

import ecoo.bpm.common.CamundaProcess;
import ecoo.bpm.entity.WorkflowRequest;
import ecoo.builder.TimeDescriptionBuilder;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskListRowBuilder {

    private Integer taskId;

    private CamundaProcess taskType;

    private String description;

    private WorkflowRequest request;

    private Date dateCreated;

    private TaskListRowBuilder() {
    }

    public static TaskListRowBuilder aTaskListRow() {
        return new TaskListRowBuilder();
    }


    public TaskListRowBuilder withTaskId(Integer taskId) {
        this.taskId = taskId;
        return this;
    }

    public TaskListRowBuilder withTaskType(CamundaProcess taskType) {
        this.taskType = taskType;
        return this;
    }

    public TaskListRowBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskListRowBuilder withWorkflowRequest(WorkflowRequest request) {
        this.request = request;
        return this;
    }

    public TaskListRowBuilder withDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public TaskListRow build() {
        return new TaskListRow(taskType.icon(), taskId, taskType.name(), description, time(), dateCreated, request);
    }

    private String time() {
        return TimeDescriptionBuilder.aTimeDescription()
                .withEvaluationDate(dateCreated)
                .build();
    }
}
