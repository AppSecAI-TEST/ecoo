package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.Signature;
import ecoo.service.SignatureService;
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
public class RemoveAssociatedSignatureTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(RemoveAssociatedSignatureTask.class);

    private SignatureService signatureService;

    @Autowired
    public RemoveAssociatedSignatureTask(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Signature signature = request.getSignature();

        signatureService.delete(signature);
        log.info("Deleting... {}", signature);
    }
}