package ecoo.bpm.tasks.user.registration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class SendActivationEmailTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(SendActivationEmailTask.class);

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");
        // TODO: Need to think about this and implement.
    }
}