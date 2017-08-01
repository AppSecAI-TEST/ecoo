package ecoo.service.impl;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.WorkflowRequest;
import ecoo.bpm.entity.WorkflowRequestDescriptionBuilder;
import ecoo.data.*;
import ecoo.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
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

    private UserService userService;

    private ShipmentCommentService shipmentCommentService;

    private ChamberService chamberService;

    private ChamberDocumentService chamberDocumentService;

    @Autowired
    public NotificationServiceImpl(VelocityEngine velocityEngine, FeatureService featureService, UserService userService, ShipmentCommentService shipmentCommentService, ChamberService chamberService, ChamberDocumentService chamberDocumentService) {
        this.velocityEngine = velocityEngine;
        this.featureService = featureService;
        this.userService = userService;
        this.shipmentCommentService = shipmentCommentService;
        this.chamberService = chamberService;
        this.chamberDocumentService = chamberDocumentService;
    }

    /**
     * Method used to send an email to notify user(s) of the shipment.
     *
     * @param user              The user who forgot their password.
     * @param plainTextPassword The new temporary password.
     * @return The message.
     */
    @Override
    public MimeMessage createForgotPasswordNotification(User user, String plainTextPassword) throws UnsupportedEncodingException, AddressException {
        Assert.notNull(user, "The variable user cannot be null.");
        Assert.hasText(plainTextPassword, "The variable plainTextPassword cannot be null.");

        final Set<User> suggestedRecipients = new HashSet<>();
        suggestedRecipients.add(user);

        final Feature nonProductionEmail = featureService.findByName(Feature.Type.NON_PRODUCTION_EMAIL);
        final Collection<InternetAddress> intendedRecipients = determineRecipient(suggestedRecipients, nonProductionEmail);

        return createMimeMessage0((MimeMessage mimeMessage) -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(intendedRecipients.toArray(new InternetAddress[intendedRecipients.size()]));

            final String subject = "Password Reset";
            message.setSubject(subject);

            final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);
            final Feature applicationRootUrl = featureService.findByName(Feature.Type.APPLICATION_ROOT_URL);

            final Map<String, Object> model = new HashMap<>();
            model.put("displayName", user.getDisplayName());
            model.put("applicationLoginUrl", applicationRootUrl.getValue());
            model.put("plainTextPassword", plainTextPassword);
            model.put("outgoingDisplayName", outgoingDisplayName.getValue().toUpperCase());

            final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "velocity/ForgotPasswordNotificationTemplate.vm", DEFAULT_ENCODING, model);
            message.setText(text, true);
        });
    }

    /**
     * Method used to send an email to notify user(s) of the shipment.
     *
     * @param shipment The shipment to notify about.
     * @return The message.
     */
    @Override
    public MimeMessage createShipmentNotification(Shipment shipment, String title) throws UnsupportedEncodingException, AddressException {
        Assert.notNull(shipment, "The variable shipment cannot be null.");

        final User assigneeUser = userService.findById(shipment.getRequestedBy());

        final Set<User> suggestedRecipients = new HashSet<>();
        suggestedRecipients.add(assigneeUser);

        final Feature nonProductionEmail = featureService.findByName(Feature.Type.NON_PRODUCTION_EMAIL);
        final Collection<InternetAddress> intendedRecipients = determineRecipient(suggestedRecipients, nonProductionEmail);

        final Set<ShipmentComment> orderedComments = new TreeSet<>((o1, o2) -> o2.getDateCreated().compareTo(o1.getDateCreated()));
        orderedComments.addAll(shipmentCommentService.findByShipmentId(shipment.getPrimaryId()));

        final Chamber chamber = chamberService.findById(shipment.getChamberId());

        return createMimeMessage0((MimeMessage mimeMessage) -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(intendedRecipients.toArray(new InternetAddress[intendedRecipients.size()]));

            final String subject = "Shipment #" + shipment.getPrimaryId() + " - " + title;
            message.setSubject(subject);

            final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);
            final Feature applicationRootUrl = featureService.findByName(Feature.Type.APPLICATION_ROOT_URL);

            // http://localhost:8888/#/app/shipments/view/3
            final String shipmentUrl = applicationRootUrl.getValue() + "#/app/shipments/view/" + shipment.getPrimaryId();

            final Map<String, Object> model = new HashMap<>();
            model.put("shipment", shipment);
            model.put("outgoingDisplayName", outgoingDisplayName.getValue().toUpperCase());
            model.put("shipmentUrl", shipmentUrl);
            model.put("comments", orderedComments);
            model.put("chamberName", chamber.getName());
            model.put("chamberEmail", chamber.getEmail());
            model.put("dateTool", new DateTool());

            final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "velocity/ShipmentNotificationTemplate.vm", DEFAULT_ENCODING, model);
            message.setText(text, true);
        });
    }

    /**
     * Method used to send an email to notify user(s) of BPM task assignment.
     *
     * @param delegateTask The Camunda delegage task.
     * @return The message.
     */
    @Override
    public MimeMessage createTaskAssignmentNotification(DelegateTask delegateTask) throws
            UnsupportedEncodingException, AddressException {
        Assert.notNull(delegateTask, "The variable delegateTask cannot be null.");

        final WorkflowRequest workflowRequest = (WorkflowRequest) delegateTask.getVariable(TaskVariables.REQUEST.variableName());
        Assert.notNull(workflowRequest, "The variable workflowRequest cannot be null.");

        final Set<User> suggestedRecipients = new HashSet<>();

        final String assignee = delegateTask.getAssignee();
        if (StringUtils.isNotBlank(assignee)) {
            final User assigneeUser = userService.findByUsername(assignee);
            suggestedRecipients.add(assigneeUser);
        } else {
            final Collection<User> allUsers = userService.findAll();
            for (final IdentityLink identityLink : delegateTask.getCandidates()) {
                final String groupId = identityLink.getGroupId();
                LOG.info("Looking for users in group {}.", groupId);
                if (StringUtils.isNotBlank(groupId)) {
                    for (User user : allUsers) {
                        if (user.getGroupIdentities().contains(groupId)) {
                            suggestedRecipients.add(user);
                        }
                    }
                }
            }
        }
        LOG.info("Found a total of {} users to notify.", suggestedRecipients.size());

        final Feature nonProductionEmail = featureService.findByName(Feature.Type.NON_PRODUCTION_EMAIL);
        final Collection<InternetAddress> intendedRecipients = determineRecipient(suggestedRecipients, nonProductionEmail);

        return createMimeMessage0((MimeMessage mimeMessage) -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(intendedRecipients.toArray(new InternetAddress[intendedRecipients.size()]));

            String workflowRequestDescription = WorkflowRequestDescriptionBuilder.aDescription()
                    .withWorkflowRequest(workflowRequest)
                    .build();
            if (workflowRequestDescription.length() > 2 && workflowRequestDescription.endsWith(".")) {
                workflowRequestDescription = workflowRequestDescription.substring(0
                        , workflowRequestDescription.length() - 1);
            }

            final String processInstanceId = delegateTask.getProcessInstanceId();

            final String subject = "Task #" + processInstanceId + " - "
                    + workflowRequestDescription;
            message.setSubject(subject);

            final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);
            final Feature applicationRootUrl = featureService.findByName(Feature.Type.APPLICATION_ROOT_URL);

            // http://localhost:8888/#/app/request/142
            final String requestUrl = applicationRootUrl.getValue() + "#/app/request/" + processInstanceId;

            final Map<String, Object> model = new HashMap<>();
            model.put("outgoingDisplayName", outgoingDisplayName.getValue().toUpperCase());
            model.put("requestUrl", requestUrl);
            model.put("workflowRequestDescription", workflowRequestDescription);
            model.put("processInstanceId", processInstanceId);
            model.put("comments", StringUtils.stripToEmpty(workflowRequest.getComments()));

            final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "velocity/TaskAssignmentNotificationTemplate.vm", DEFAULT_ENCODING, model);
            message.setText(text, true);
        });
    }

    /**
     * Method used to send an email to confirm the new user was successfully imported in the system.
     *
     * @param newUser The newly imported user.
     * @param chamber The chamber the application is sent to.
     * @param company The company associated to the new user.
     * @return The message.
     * @throws IllegalArgumentException If newUser is null.
     */
    @Override
    public MimeMessage createNewUserConfirmationEmail(User newUser, Chamber chamber, Company company) throws
            UnsupportedEncodingException, AddressException {
        Assert.notNull(newUser, "The variable newUser cannot be null.");
        Assert.notNull(chamber, "The variable chamber cannot be null.");
        Assert.notNull(company, "The variable chamber cannot be null.");

        final Set<User> suggestedRecipients = new HashSet<>();
        suggestedRecipients.add(newUser);

        final Feature nonProductionEmail = featureService.findByName(Feature.Type.NON_PRODUCTION_EMAIL);
        final Collection<InternetAddress> intendedRecipients = determineRecipient(suggestedRecipients, nonProductionEmail);

        final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);

        final ChamberDocument letterOfAuthority = chamberDocumentService.findByChamberAndType(chamber.getPrimaryId()
                , DocumentTypes.LetterOfAuthority.getPrimaryId());
        Assert.notNull(letterOfAuthority, String.format("System cannot complete request. No letter of authority " +
                "template found for chamber <%s>.", chamber.getName()));

        final Feature gatewayUrl = featureService.findByName(Feature.Type.GATEWAY_URL);

        // http://localhost:3331
        final String letterOfAuthorityUrl = gatewayUrl.getValue() + "/api/chambers/docs/chamber/" + chamber.getPrimaryId()
                + "/documentType/LOA/download";

        final String formalUndertakingUrl;
        if (StringUtils.isBlank(company.getStatus()) || company.isInStatus(CompanyStatus.PendingDocumentation)) {
            formalUndertakingUrl = gatewayUrl.getValue() + "/api/chambers/docs/chamber/" + chamber.getPrimaryId()
                    + "/documentType/FU/download";
        } else {
            formalUndertakingUrl = "";
        }

        final Feature applicationRootUrl = featureService.findByName(Feature.Type.APPLICATION_ROOT_URL);
        final String applicationLoginUrl = applicationRootUrl.getValue() + "#/login";

        return createMimeMessage0((MimeMessage mimeMessage) -> {
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(intendedRecipients.toArray(new InternetAddress[intendedRecipients.size()]));

            final String subject = "Confirmation E-Mail";
            message.setSubject(subject);

            final Map<String, Object> model = new HashMap<>();
            model.put("outgoingDisplayName", outgoingDisplayName.getValue().toUpperCase());
            model.put("applicationLoginUrl", applicationLoginUrl);
            model.put("displayName", newUser.getDisplayName());
            model.put("chamberName", chamber.getName());
            model.put("chamberEmail", chamber.getEmail());
            model.put("letterOfAuthorityUrl", letterOfAuthorityUrl);
            model.put("formalUndertakingUrl", formalUndertakingUrl);

            final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "velocity/NewUserNotificationTemplate.vm", DEFAULT_ENCODING, model);
            message.setText(text, true);
        });
    }

    private Collection<InternetAddress> determineRecipient(Set<User> suggestedRecipients, Feature
            nonProductionEmail) throws
            AddressException, UnsupportedEncodingException {
        final Collection<InternetAddress> intendedRecipients = new ArrayList<>();
        // Only send email to the intended users if the email is an exception email OR if we are
        // running in the production environment. If the non production email is blank then the
        // email will be email to the intended recipient.
        if (StringUtils.isBlank(nonProductionEmail.getValue())) {
            for (User recipient : suggestedRecipients) {
                intendedRecipients.add(new InternetAddress(recipient.getPrimaryEmailAddress(), recipient
                        .getDisplayName()));
            }
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

            final Feature smtpUserFeature = featureService.findByName(Feature.Type.SMTP_USERNAME);
            Assert.notNull(smtpUserFeature, "SMTP_USERNAME not found.");
            final String smtpUser = StringUtils.isBlank(smtpUserFeature.getValue()) ? "" : smtpUserFeature.getValue();

            final Feature smtpPwdFeature = featureService.findByName(Feature.Type.SMTP_PWD);
            Assert.notNull(smtpPwdFeature, "SMTP_PWD not found.");
            final String smtpPwd = StringUtils.isBlank(smtpPwdFeature.getValue()) ? "" : smtpPwdFeature.getValue();

            final Feature smtpDebugFeature = featureService.findByName(Feature.Type.SMTP_DEBUG);
            Assert.notNull(smtpDebugFeature, "SMTP_DEBUG not found.");
            final String smtpDebug = StringUtils.isBlank(smtpDebugFeature.getValue()) ? "false" : smtpDebugFeature.getValue();

            final Feature smtpPort = featureService.findByName(Feature.Type.SMTP_PORT);
            Assert.notNull(smtpPort, "SMTP_PORT not found.");
            Assert.hasText(smtpPort.getValue(), "SMTP_PORT not found.");

            final Properties properties = new Properties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.host", smtpServer.getValue());
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.debug", smtpDebug);
            properties.put("mail.smtp.port", smtpPort.getValue());

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
                        smtpServer.getValue().trim(), recipients));

                if (mimeMessage.getSentDate() == null) {
                    mimeMessage.setSentDate(new Date());
                }

                final Feature outgoingDisplayName = featureService.findByName(Feature.Type.OUTGOING_DISPLAY_NAME);
                final Feature outgoingEmail = featureService.findByName(Feature.Type.OUTGOING_EMAIL);

                Address sender = new InternetAddress(outgoingEmail.getValue(), outgoingDisplayName.getValue());
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

//    private void addApplicationLogo(String systemDir, MimeMessage mimeMessage, String text) throws MessagingException, IOException {
//        Multipart multipart = new MimeMultipart("related");
//
//        MimeBodyPart htmlPart = new MimeBodyPart();
//        htmlPart.setText(text, "utf-8", "html");
//        multipart.addBodyPart(htmlPart);
//
//        MimeBodyPart imgPart = new MimeBodyPart();
//        imgPart.attachFile(FileUtils.resolveDir(systemDir) + "logo.png");
//        imgPart.setContentID("<ApplicationImage>");
//        multipart.addBodyPart(imgPart);
//
//        mimeMessage.setContent(multipart);
//    }
}
