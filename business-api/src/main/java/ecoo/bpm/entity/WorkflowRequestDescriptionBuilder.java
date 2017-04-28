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
                return String.format("User %s is requesting user %s to be approved and registered on the system."
                        , registerUserAccountRequest.getRequestingUser().getDisplayName()
                        , registerUserAccountRequest.getUser().getDisplayName());
            default:
                throw new UnsupportedOperationException(String.format("Process type %s not supported.", workflowRequest.getType()));
        }
    }
}
