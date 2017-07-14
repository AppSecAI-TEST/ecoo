package ecoo.bpm.tasks.user.forgotPassword;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.ForgotPasswordRequest;
import ecoo.data.User;
import ecoo.service.UserService;
import ecoo.util.RandomWordGenerator;
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
public class ResetPasswordTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ResetPasswordTask.class);

    private UserService userService;

    @Autowired
    public ResetPasswordTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final ForgotPasswordRequest request = (ForgotPasswordRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final User targetUser = (User) delegateExecution.getVariable("targetUser");

        request.setEmailAddress(targetUser.getPrimaryEmailAddress());

        final String plainTextPassword = generatePlaintextPassword();
        delegateExecution.setVariable("plainTextPassword", plainTextPassword);

        log.info("Password reset to <{}> for user <{}>.", plainTextPassword, targetUser.getDisplayName());

        userService.save(userService.setPassword(targetUser, plainTextPassword, false));
        log.info(targetUser.toString());
    }

    private String generatePlaintextPassword() {
        final RandomWordGenerator randomWordGenerator = new RandomWordGenerator();
        return randomWordGenerator.generate(1, 20)[0];
    }
}