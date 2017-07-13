package ecoo.bpm.tasks.user.event;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.ChamberGroupIdentityFactory;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Component
public class UserRegistrationTaskAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationTaskAssignmentHandler.class);

    @SuppressWarnings("Duplicates")
    @Override
    public void notify(DelegateTask delegateTask) {
        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateTask.
                getVariable(TaskVariables.REQUEST.variableName());

        final String chamberGroupIdentity = ChamberGroupIdentityFactory.build(request.getChamber().getPrimaryId());
        delegateTask.setAssignee(null);
        delegateTask.addCandidateGroup(chamberGroupIdentity);
        log.info("Task assigned to candidate group associated to chambers {} <{}>."
                , request.getChamber().getName(), chamberGroupIdentity);
    }
}
