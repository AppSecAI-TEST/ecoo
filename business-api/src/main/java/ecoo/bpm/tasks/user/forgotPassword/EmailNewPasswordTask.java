package ecoo.bpm.tasks.user.forgotPassword;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.ForgotPasswordRequest;
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
 * @since May 2017
 */
@SuppressWarnings("unused")
@Component
public class EmailNewPasswordTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(EmailNewPasswordTask.class);

    private NotificationService notificationService;

    @Autowired
    public EmailNewPasswordTask(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final ForgotPasswordRequest request = (ForgotPasswordRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final User targetUser = (User) delegateExecution.getVariable("targetUser");
        final String plainTextPassword = (String) delegateExecution.getVariable("plainTextPassword");

        final MimeMessage forgotPasswordNotification = notificationService.createForgotPasswordNotification(targetUser, plainTextPassword);
        notificationService.send(forgotPasswordNotification, false);
    }
}