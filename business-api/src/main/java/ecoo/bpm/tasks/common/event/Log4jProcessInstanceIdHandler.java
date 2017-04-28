package ecoo.bpm.tasks.common.event;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class Log4jProcessInstanceIdHandler implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        final String processInstanceId = delegateExecution.getProcessInstanceId();
        MDC.put("processInstanceId", processInstanceId);
    }
}
