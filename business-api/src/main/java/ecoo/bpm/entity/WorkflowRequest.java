package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ecoo.bpm.common.CamundaProcess;
import ecoo.builder.TimeDescriptionBuilder;
import ecoo.data.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisterCompanyAccountRequest.class, name = "RegisterCompanyAccountRequest"),
        @JsonSubTypes.Type(value = RegisterUserAccountRequest.class, name = "RegisterUserAccountRequest"),
        @JsonSubTypes.Type(value = PasswordResetRequest.class, name = "PasswordResetRequest"),
        @JsonSubTypes.Type(value = NewShipmentRequest.class, name = "NewShipmentRequest"),
        @JsonSubTypes.Type(value = CreateSignatureBpmRequest.class, name = "CreateSignatureRequest"),
        @JsonSubTypes.Type(value = ForgotPasswordRequest.class, name = "ForgotPasswordRequest")
})
public abstract class WorkflowRequest implements Serializable {

    private static final long serialVersionUID = -2895556419346716313L;

    private String processInstanceId;

    private String businessKey;

    private Date dateCreated;

    private String dateCreatedTimeDescription;

    private User requestingUser;

    private User assignee;

    private List<String> candidateGroups = new ArrayList<>();

    private String comments;

    public abstract CamundaProcess getType();

    public final String getProcessInstanceId() {
        return processInstanceId;
    }

    public final void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public final String getBusinessKey() {
        return businessKey;
    }

    public final void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public final Date getDateCreated() {
        return dateCreated;
    }

    public final void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonGetter
    public final String getDateCreatedTimeDescription() {
        if (dateCreatedTimeDescription == null && this.dateCreated != null) {
            dateCreatedTimeDescription = TimeDescriptionBuilder.aTimeDescription()
                    .withEvaluationDate(this.dateCreated)
                    .build();
        }
        return dateCreatedTimeDescription;
    }

    public final User getRequestingUser() {
        return requestingUser;
    }

    public final void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public List<String> getCandidateGroups() {
        return candidateGroups;
    }

    public void setCandidateGroups(List<String> candidateGroups) {
        this.candidateGroups = candidateGroups;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "WorkflowRequest{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", dateCreated=" + dateCreated +
                ", requestingUser=" + requestingUser +
                ", assignee=" + assignee +
                ", candidateGroups=" + candidateGroups +
                '}';
    }
}