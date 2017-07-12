package ecoo.bpm.tasks.user.event;

import ecoo.service.NotificationService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class TaskNotificationAssignmentHandler implements TaskListener {

    private final Logger log = LoggerFactory.getLogger(TaskNotificationAssignmentHandler.class);

    private NotificationService notificationService;

    @Autowired
    public TaskNotificationAssignmentHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        try {
            final MimeMessage message = notificationService.createTaskAssignmentNotification(delegateTask);
            log.info("Attempting to send notification to: {}", Arrays.toString(message.getAllRecipients()));

            notificationService.send(message, false);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
