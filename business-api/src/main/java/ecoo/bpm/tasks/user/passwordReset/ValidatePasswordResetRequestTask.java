package ecoo.bpm.tasks.user.passwordReset;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.PasswordResetRequest;
import ecoo.data.Role;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Component
public class ValidatePasswordResetRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ValidatePasswordResetRequestTask.class);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final PasswordResetRequest request = (PasswordResetRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        assertRoles(request);
    }

    private void assertRoles(PasswordResetRequest request) {
        if (request.getUser().equals(request.getRequestingUser())) {
            log.info("Requesting user is the same user as password reset >> no problems.");
        } else {
            log.info("Requesting user differs from user as password reset >> check role.");
            if (!request.getRequestingUser().hasRole(Role.ROLE_SYSADMIN)) {
                throw new BusinessRuleViolationException(String.format("System cannot complete request. The user %s" +
                                " is the requesting user to change the password for user %s. Insufficient privileges, only" +
                                " System Administrator can request password reset for other users."
                        , request.getRequestingUser().getDisplayName(), request.getUser().getDisplayName()));
            }
        }
    }
}