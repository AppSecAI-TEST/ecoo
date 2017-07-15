package ecoo.bpm.tasks.user.forgotPassword;

import ecoo.bpm.BusinessRuleViolationException;
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
public class ThrowNoUserFoundExceptionTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ThrowNoUserFoundExceptionTask.class);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final String message = "System cannot complete request. No user found for email address / username.";
        throw new BusinessRuleViolationException(message);
    }
}