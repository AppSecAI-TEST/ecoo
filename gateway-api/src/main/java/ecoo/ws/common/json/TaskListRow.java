package ecoo.ws.common.json;


import ecoo.bpm.entity.WorkflowRequest;

import java.util.Date;
import java.util.Objects;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TaskListRow {

    private String icon;

    private Integer taskId;

    private String taskType;

    private String description;

    private String time;

    private Date dateCreated;

    private WorkflowRequest request;

    public TaskListRow(String icon, Integer taskId, String taskType, String description, String time, Date dateCreated
            , WorkflowRequest request) {
        this.icon = icon;
        this.taskId = taskId;
        this.taskType = taskType;
        this.description = description;
        this.time = time;
        this.dateCreated = dateCreated;
        this.request = request;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public WorkflowRequest getRequest() {
        return request;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskListRow that = (TaskListRow) o;
        return Objects.equals(this.taskId, that.taskId);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.taskId);
    }
}

