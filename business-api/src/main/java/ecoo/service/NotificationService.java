package ecoo.service;

import ecoo.data.Chamber;
import ecoo.data.Shipment;
import ecoo.data.User;
import org.camunda.bpm.engine.delegate.DelegateTask;

import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface NotificationService {
    
    /**
     * Method used to send an email to notify user(s) of the shipment.
     *
     * @param user              The user who forgot their password.
     * @param plainTextPassword The new temporary password.
     * @return The message.
     */
    MimeMessage createForgotPasswordNotification(User user, String plainTextPassword) throws UnsupportedEncodingException, AddressException;

    /**
     * Method used to send an email to notify user(s) of the shipment.
     *
     * @param shipment The shipment to notify about.
     * @param title    The email title.
     * @return The message.
     */
    MimeMessage createShipmentNotification(Shipment shipment, String title) throws UnsupportedEncodingException, AddressException;

    /**
     * Method used to send an email to notify user(s) of BPM task assignment.
     *
     * @param delegateTask The Camunda delegage task.
     * @return The message.
     */
    MimeMessage createTaskAssignmentNotification(DelegateTask delegateTask) throws UnsupportedEncodingException, AddressException;

    /**
     * Method used to send an email to confirm the new user was successfully imported in the system.
     *
     * @param newUser The newly imported user.
     * @param chamber The chamber the application is sent to.
     * @return The message.
     */
    MimeMessage createNewUserConfirmationEmail(User newUser, Chamber chamber) throws UnsupportedEncodingException, AddressException;

    void send(MimeMessage mimeMessage);

    void send(MimeMessage mimeMessage, boolean wait);
}
