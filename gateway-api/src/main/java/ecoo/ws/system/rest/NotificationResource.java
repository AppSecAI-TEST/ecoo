package ecoo.ws.system.rest;

import ecoo.data.Shipment;
import ecoo.service.NotificationService;
import ecoo.service.ShipmentService;
import ecoo.ws.common.rest.BaseResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@RestController
@RequestMapping("/api/notification")
public class NotificationResource extends BaseResource {

    private final static Logger LOG = LoggerFactory.getLogger(NotificationResource.class);

    private NotificationService notificationService;

    private ShipmentService shipmentService;

    @Autowired
    public NotificationResource(NotificationService notificationService, ShipmentService shipmentService) {
        this.notificationService = notificationService;
        this.shipmentService = shipmentService;
    }

    @RequestMapping(value = "/send" +
            "/shipmentId/{shipmentId}" +
            "/s/{smtpServer}" +
            "/u/{smtpUser}" +
            "/p/{smtpPwd}" +
            "/d/{smtpDebug}" +
            "/port/{smtpPort}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> sendTestEmail(@PathVariable Integer shipmentId
            , @PathVariable String smtpServer
            , @PathVariable String smtpUser
            , @PathVariable String smtpPwd
            , @PathVariable String smtpDebug
            , @PathVariable String smtpPort) throws UnsupportedEncodingException, AddressException {
        Shipment shipment = shipmentService.findById(shipmentId);

        final MimeMessage newUserConfirmationEmail = notificationService.createShipmentNotification(shipment, "TEST");
        sendDirectly(newUserConfirmationEmail
                , clean(smtpServer)
                , clean(smtpUser)
                , clean(smtpPwd)
                , clean(smtpDebug)
                , clean(smtpPort));

        return ResponseEntity.ok(true);
    }

    @SuppressWarnings("Duplicates")
    private void sendDirectly(MimeMessage mimeMessage, String smtpServer
            , String smtpUser
            , String smtpPwd
            , String smtpDebug
            , String smtpPort) {
        Assert.notNull(mimeMessage);


        final Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", smtpServer);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", smtpDebug);
        properties.put("mail.smtp.port", smtpPort);

        LOG.info(String.format("Using... {username=%s, password=%s}", smtpUser, smtpPwd));

        final Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtpUser, smtpPwd);
                    }
                });
        try {
            final StringBuilder recipients = new StringBuilder();
            for (Address a : mimeMessage.getRecipients(Message.RecipientType.TO)) {
                recipients.append(((InternetAddress) a).getAddress());
                recipients.append(" ");
            }
            LOG.info(String.format("smtp server set to %s -> attempting to send email to %s.",
                    smtpServer.trim(), recipients));

            if (mimeMessage.getSentDate() == null) {
                mimeMessage.setSentDate(new Date());
            }

            final String outgoingDisplayName = "ECOO Test";
            Address sender = new InternetAddress("test@ecoo.co.za", outgoingDisplayName);
            mimeMessage.setFrom(sender);

            String messageId = mimeMessage.getMessageID();
            mimeMessage.saveChanges();
            if (messageId != null) {
                mimeMessage.setHeader("Message-ID", messageId);
            }

            Transport transport = null;
            try {
                transport = session.getTransport();
                transport.connect();
                transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            } finally {
                if (transport != null) {
                    try {
                        transport.close();
                    } catch (final Throwable t) {
                        LOG.warn(t.getMessage(), t);
                    }
                }
            }
        } catch (final Throwable t) {
            LOG.warn(t.getMessage(), t);
        }
    }

    private String clean(String str) {
        if (StringUtils.isBlank(str) || str.equalsIgnoreCase("NULL")) return "";
        return str;
    }
}
