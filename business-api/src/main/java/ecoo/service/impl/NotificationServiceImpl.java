package ecoo.service.impl;

import ecoo.data.Chamber;
import ecoo.data.Feature;
import ecoo.data.User;
import ecoo.service.FeatureService;
import ecoo.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Service(value = "notificationService")
public final class NotificationServiceImpl implements NotificationService {

    private final static Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    private static String DEFAULT_ENCODING = "ISO-8859-1";

    private VelocityEngine velocityEngine;

    private FeatureService featureService;

    @Autowired
    public NotificationServiceImpl(VelocityEngine velocityEngine, FeatureService featureService) {
        this.velocityEngine = velocityEngine;
        this.featureService = featureService;
    }

    /**
     * Method used to send an email to confirm the new user was successfully imported in the system.
     *
     * @param newUser The newly imported user.
     * @param chamber The chamber the application is sent to.
     * @return The message.
     * @throws IllegalArgumentException If newUser is null.
     */
    @Override
    public MimeMessage createNewUserConfirmationEmail(User newUser, Chamber chamber) throws UnsupportedEncodingException, AddressException {
        Assert.notNull(newUser, "The variable newUser cannot be null.");
        Assert.notNull(chamber, "The variable chamber cannot be null.");

        final Feature nonProductionEmail = featureService.findByName(Feature.Type.NON_PRODUCTION_EMAIL);
        final Collection<InternetAddress> intendedRecipients = determineRecipient(newUser, nonProductionEmail);

        return createMimeMessage0((MimeMessage mimeMessage) -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(intendedRecipients.toArray(new InternetAddress[intendedRecipients.size()]));

            final String subject = "Confirmation E-Mail";
            message.setSubject(subject);

            final Feature outgoingEmail = featureService.findByName(Feature.Type.SMTP_USERNAME);
            final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);
            final Feature applicationRootUrl = featureService.findByName(Feature.Type.APPLICATION_ROOT_URL);

            final String applicationLoginUrl = applicationRootUrl.getValue() + "/#/login";

            final Map<String, Object> model = new HashMap<>();
            model.put("outgoingEmail", outgoingEmail.getValue());
            model.put("outgoingDisplayName", outgoingDisplayName.getValue().toUpperCase());
            model.put("applicationLoginUrl", applicationLoginUrl);
            model.put("displayName", newUser.getDisplayName());
            model.put("chamberName", chamber.getName());

            final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "velocity/NewUserNotificationTemplate.vm", DEFAULT_ENCODING, model);
            message.setText(text, true);
        });
    }

    private Collection<InternetAddress> determineRecipient(User recipient, Feature nonProductionEmail) throws AddressException, UnsupportedEncodingException {
        final Collection<InternetAddress> intendedRecipients = new ArrayList<>();
        // Only send email to the intended users if the email is an exception email OR if we are
        // running in the production environment. If the non production email is blank then the
        // email will be email to the intended recipient.
        if (StringUtils.isBlank(nonProductionEmail.getValue())) {
            intendedRecipients.add(new InternetAddress(recipient.getPrimaryEmailAddress(), recipient
                    .getDisplayName()));
        } else {
            intendedRecipients.add(new InternetAddress(nonProductionEmail.getValue()));
        }
        return intendedRecipients;
    }

    @Override
    public void send(MimeMessage mimeMessage) {
        Assert.notNull(mimeMessage);
        send(mimeMessage, true);
    }

    @Override
    public void send(MimeMessage mimeMessage, boolean wait) {
        Assert.notNull(mimeMessage, "The variable mimeMessage cannot be null.");
        if (wait) {
            doSend0(mimeMessage);

        } else {
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    doSend0(mimeMessage);
                }
            }, 5000);
        }
    }

    private MimeMessage createMimeMessage0(MimeMessagePreparator mimeMessagePreparator) {
        final Feature smtpServer = featureService.findByName(Feature.Type.SMTP_SERVER);

        final Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");

        if (StringUtils.isNotBlank(smtpServer.getValue())) {
            properties.setProperty("mail.host", smtpServer.getValue());
        }

        final Session session = Session.getDefaultInstance(properties);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessagePreparator.prepare(mimeMessage);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return mimeMessage;
    }

    private void doSend0(final MimeMessage mimeMessage) {
        Assert.notNull(mimeMessage);

        // So no SMTP server is found, therefore simply return.
        final Feature smtpServer = featureService.findByName(Feature.Type.SMTP_SERVER);
        if (StringUtils.isBlank(smtpServer.getValue())) {
            LOG.info("No email sent as smtp server not defined.");

        } else {
            LOG.info(String.format("SMTP server set to %s >> send email.", smtpServer.getValue()));

            final Feature smtpUser = featureService.findByName(Feature.Type.SMTP_USERNAME);
            Assert.notNull(smtpUser, "SMTP_USERNAME not found.");
            Assert.hasText(smtpUser.getValue(), "SMTP_USERNAME not found.");

            final Feature smtpPwd = featureService.findByName(Feature.Type.SMTP_PWD);
            Assert.notNull(smtpPwd, "SMTP_PWD not found.");
            Assert.hasText(smtpPwd.getValue(), "SMTP_PWD not found.");

            final Feature smtpDebug = featureService.findByName(Feature.Type.SMTP_DEBUG);
            Assert.notNull(smtpDebug, "SMTP_DEBUG not found.");
            Assert.hasText(smtpDebug.getValue(), "SMTP_DEBUG not found.");

            final Feature smtpPort = featureService.findByName(Feature.Type.SMTP_PORT);
            Assert.notNull(smtpPort, "SMTP_PORT not found.");
            Assert.hasText(smtpPort.getValue(), "SMTP_PORT not found.");

            final Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.host", smtpServer.getValue());
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.debug", smtpDebug.getValue());
            properties.put("mail.smtp.port", smtpPort.getValue());

            LOG.info(String.format("Using... {username=%s, password=%s}", smtpUser.getValue(), smtpPwd.getValue()));

            final Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(smtpUser.getValue(), smtpPwd.getValue());
                        }
                    });
            try {
                final StringBuilder recipients = new StringBuilder();
                for (Address a : mimeMessage.getRecipients(Message.RecipientType.TO)) {
                    recipients.append(((InternetAddress) a).getAddress());
                    recipients.append(" ");
                }
                LOG.info(String.format("smtp server set to %s -> attempting to send email to %s.",
                        smtpServer.getValue().trim(), recipients));

                if (mimeMessage.getSentDate() == null) {
                    mimeMessage.setSentDate(new Date());
                }

                final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);

                //final String serverName = InetAddress.getLocalHost().getHostName();
                Address sender = new InternetAddress(smtpUser.getValue(), outgoingDisplayName.getValue());
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
    }
}
