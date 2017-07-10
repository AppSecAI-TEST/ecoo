package ecoo.service;

import ecoo.data.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface NotificationService {

    /**
     * Method used to send an email to confirm the new user was successfully imported in the system.
     *
     * @param newUser The newly imported user.
     * @return The message.
     * @throws IllegalArgumentException If newUser is null.
     */
    MimeMessage createNewUserConfirmationEmail(User newUser) throws UnsupportedEncodingException, AddressException;

    void send(MimeMessage mimeMessage);

    void send(MimeMessage mimeMessage, boolean wait);
}
