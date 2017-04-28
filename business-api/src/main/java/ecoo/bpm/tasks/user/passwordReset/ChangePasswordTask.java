package ecoo.bpm.tasks.user.passwordReset;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.PasswordResetRequest;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Component
public class ChangePasswordTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ChangePasswordTask.class);

    private UserService userService;

    @Autowired
    public ChangePasswordTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final PasswordResetRequest request = (PasswordResetRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        userService.save(userService.setPassword(request.getUser()
                , request.getPlainTextPassword(), request.isForcePasswordExpired()));
    }
}