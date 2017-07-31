package ecoo.bpm.tasks.user.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class UploadLetterOfAuthorityDocumentTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(UploadLetterOfAuthorityDocumentTaskAssignmentHandler.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateTask.
                getVariable(TaskVariables.REQUEST.variableName());

        final String assignee = request.getRequestingUser().getUsername();
        delegateTask.setAssignee(assignee);

        log.info("Task assigned to assignee {} <{}>.", request.getRequestingUser().getDisplayName(), assignee);
    }
}
