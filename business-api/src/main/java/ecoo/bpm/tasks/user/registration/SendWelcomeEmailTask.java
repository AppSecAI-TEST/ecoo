package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.User;
import ecoo.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class SendWelcomeEmailTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(SendWelcomeEmailTask.class);

    private NotificationService notificationService;

    @Autowired
    public SendWelcomeEmailTask(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");
        try {
            final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                    getVariable(TaskVariables.REQUEST.variableName());

            final User newUser = request.getUser();
            log.info(String.format("Attempting to send confirmation email to: %s", newUser.getPrimaryEmailAddress()));

            final MimeMessage newUserConfirmationEmail = notificationService
                    .createNewUserConfirmationEmail(newUser, request.getChamber(), request.getCompany());
            notificationService.send(newUserConfirmationEmail, false);

        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}