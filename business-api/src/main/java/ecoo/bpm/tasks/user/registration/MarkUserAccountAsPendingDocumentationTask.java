package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.User;
import ecoo.data.UserStatus;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class MarkUserAccountAsPendingDocumentationTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(MarkUserAccountAsPendingDocumentationTask.class);

    private UserService userService;

    @Autowired
    public MarkUserAccountAsPendingDocumentationTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());
        request.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        final User user = request.getUser();
        user.setStatus(UserStatus.PendingDocumentation.id());

        log.info("Attempting to save user... {}", user.toString());
        userService.save(user);
    }
}