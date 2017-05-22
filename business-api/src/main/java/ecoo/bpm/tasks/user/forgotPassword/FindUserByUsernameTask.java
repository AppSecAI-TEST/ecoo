package ecoo.bpm.tasks.user.forgotPassword;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.ForgotPasswordRequest;
import ecoo.data.User;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class FindUserByUsernameTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(FindUserByUsernameTask.class);

    private UserService userService;

    @Autowired
    public FindUserByUsernameTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final ForgotPasswordRequest request = (ForgotPasswordRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final User targetUser = userService.findByUsername(request.getUsername());
        log.info("Find user by email address {}: {}", request.getEmailAddress(), targetUser);

        delegateExecution.setVariable("targetUser", targetUser);
    }
}