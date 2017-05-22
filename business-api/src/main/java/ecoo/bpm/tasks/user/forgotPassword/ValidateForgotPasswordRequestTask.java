package ecoo.bpm.tasks.user.forgotPassword;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.ForgotPasswordRequest;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class ValidateForgotPasswordRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ValidateForgotPasswordRequestTask.class);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final ForgotPasswordRequest request = (ForgotPasswordRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());
        assertRequest(request);
    }

    private void assertRequest(ForgotPasswordRequest request) {
        if (StringUtils.isBlank(request.getEmailAddress()) && StringUtils.isBlank(request.getUsername())) {
            throw new BusinessRuleViolationException("System cannot complete request. Either the \"Email Address\" or " +
                    "\"Username\" is required.");
        }
    }
}