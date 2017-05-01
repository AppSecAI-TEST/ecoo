package ecoo.bpm.entity;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class WorkflowRequestDescriptionBuilder {
    private WorkflowRequest workflowRequest;

    private WorkflowRequestDescriptionBuilder() {
    }

    public static WorkflowRequestDescriptionBuilder aDescription() {
        return new WorkflowRequestDescriptionBuilder();
    }

    public WorkflowRequestDescriptionBuilder withWorkflowRequest(WorkflowRequest workflowRequest) {
        this.workflowRequest = workflowRequest;
        return this;
    }

    public String build() {
        switch (workflowRequest.getType()) {
            case UserRegistration:
                final RegisterUserAccountRequest registerUserAccountRequest = (RegisterUserAccountRequest) workflowRequest;
                return String.format("New user %s from %s is requesting access to %s."
                        , registerUserAccountRequest.getUser().getDisplayName()
                        , registerUserAccountRequest.getCompany().getName()
                        , registerUserAccountRequest.getChamber().getName());
            default:
                throw new UnsupportedOperationException(String.format("Process type %s not supported.", workflowRequest.getType()));
        }
    }
}
