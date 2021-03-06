package ecoo.service.impl;

import ecoo.bpm.BusinessRuleViolationException;
import ecoo.bpm.common.CamundaProcess;
import ecoo.bpm.common.TaskDefinition;
import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.constants.UserRegistrationVariables;
import ecoo.bpm.entity.*;
import ecoo.data.*;
import ecoo.security.UserAuthentication;
import ecoo.service.FeatureService;
import ecoo.service.ShipmentService;
import ecoo.service.UserService;
import ecoo.service.WorkflowService;
import ecoo.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.variable.Variables.createVariables;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Component
public class CamundaRuntimeWorkflowServiceImpl implements WorkflowService {

    private static final Logger LOG = LoggerFactory.getLogger(CamundaRuntimeWorkflowServiceImpl.class);

    private RuntimeService runtimeService;

    private FeatureService featureService;

    private TaskService taskService;

    private UserService userService;

    private ShipmentService shipmentService;


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public CamundaRuntimeWorkflowServiceImpl(RuntimeService runtimeService, FeatureService featureService, TaskService taskService
            , UserService userService
            , ShipmentService shipmentService) {
        this.runtimeService = runtimeService;
        this.featureService = featureService;
        this.taskService = taskService;
        this.userService = userService;
        this.shipmentService = shipmentService;
    }

    /**
     * Method to execute the forgot password process.
     *
     * @param request The forgot password request.
     * @return The response.
     */
    @Override
    public ForgotPasswordResponse forgetPassword(ForgotPasswordRequest request) {
        Assert.notNull(request, "The request cannot be null.");

        final String businessKey = "FP-" + UUID.randomUUID().toString().replace("-", "");
        LOG.info("businessKey: {}", businessKey);

        request.setBusinessKey(businessKey);
        request.setDateCreated(new Date());

        request.setEmailAddress(StringUtils.stripToNull(request.getEmailAddress()));
        request.setUsername(StringUtils.stripToNull(request.getUsername()));

        final Map<String, Object> variables = new HashMap<>();
        variables.put(TaskVariables.REQUEST.variableName(), request);

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CamundaProcess.ForgotPassword.id()
                , businessKey, variables);
        LOG.info("Forgot password process started: " + processInstance.getProcessInstanceId());

        return new ForgotPasswordResponse(processInstance.getProcessInstanceId()
                , request.getEmailAddress());
    }

    /**
     * Method to request a new shipment.
     *
     * @param request The new shipment request.
     * @return The new shipment response.
     */
    @Override
    public NewShipmentResponse requestNewShipment(NewShipmentRequest request) {
        Assert.notNull(request, "The request cannot be null.");

        final String processInstanceId = request.getShipment().getProcessInstanceId();
        if (StringUtils.isNotBlank(processInstanceId)) {
            throw new IllegalArgumentException(String.format("System cannot complete request. Shipment %s is already " +
                    "associated with process %s.", request.getShipment().getPrimaryId(), processInstanceId));
        }

        final User requestingUser = request.getRequestingUser();
        Assert.notNull(requestingUser, "The requestingUser cannot be null.");

        final String businessKey = "NSR-" + request.getShipment().getPrimaryId();
        LOG.info("businessKey: {}", businessKey);

        request.setBusinessKey(businessKey);
        request.setDateCreated(new Date());

        final Map<String, Object> variables = new HashMap<>();
        variables.put(TaskVariables.REQUEST.variableName(), request);

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CamundaProcess.NewShipmentRequest.id()
                , businessKey, variables);
        LOG.info("Request new shipment process started: " + processInstance.getProcessInstanceId());

        return new NewShipmentResponse(request.getShipment(), processInstance.getProcessInstanceId());
    }

    /**
     * Returns the active tasks for the given type.
     *
     * @param definition The task definition.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findActiveTasksByTaskDefinition(String definition) {
        Assert.notNull(definition, "The variable definition cannot be null or blank.");
        final TaskDefinition taskDefinition = TaskDefinition.valueOf(definition);
        return findActiveTasksByTaskDefinition(taskDefinition);
    }

    /**
     * Returns the active tasks for the given type.
     *
     * @param taskDefinition The task definition.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findActiveTasksByTaskDefinition(TaskDefinition taskDefinition) {
        Assert.notNull(taskDefinition, "The variable taskDefinition cannot be null or blank.");

        final List<WorkflowRequest> tasks = new ArrayList<>();
        for (Task task : taskService.createTaskQuery().taskDefinitionKey(taskDefinition.name()).active().list()) {
            final WorkflowRequest request = (WorkflowRequest) taskService.getVariable(task.getId()
                    , TaskVariables.REQUEST.variableName());
            tasks.add(request);
        }
        return tasks;
    }

    @Override
    public List<WorkflowRequest> findAssignedToTasks(String username) {
        Assert.hasText(username, "System cannot complete request. username cannot be null.");

        final Collection<Task> tasks = new ArrayList<>();
        tasks.addAll(taskService.createTaskQuery().taskAssignee(username)
                .active().list());

        final List<String> candidateGroups = candidateGroups(username);
        if (!candidateGroups.isEmpty()) {
            tasks.addAll(taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups)
                    .active().list());
        }

        final List<WorkflowRequest> workflowRequests = new ArrayList<>();
        for (Task task : tasks) {
            workflowRequests.add(buildWorkflowRequest(task));
        }
        return workflowRequests;
    }

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username        The assignee username.
     * @param taskDefinitions The task definition id array.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findAssignedToTasks(String username, String[] taskDefinitions) {
        Assert.hasText(username, "The variable username cannot be null.");
        Assert.notNull(taskDefinitions, "The variable taskDefinitions cannot be null or blank.");

        final List<WorkflowRequest> tasks = new ArrayList<>();
        for (String definition : taskDefinitions) {
            tasks.addAll(findAssignedToTasks(username, definition));
        }
        return tasks;
    }

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username   The assignee username.
     * @param definition The task definition id.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findAssignedToTasks(String username, String definition) {
        Assert.hasText(username, "System cannot complete request. username cannot be null.");
        Assert.hasText(definition, "definition cannot be null or blank.");

        final TaskDefinition taskDefinition = TaskDefinition.valueOf(definition);
        Assert.notNull(taskDefinition, String.format("No task definition found for name %s.", definition));

        final Collection<Task> tasks = new ArrayList<>();
        tasks.addAll(taskService.createTaskQuery().taskAssignee(username).taskDefinitionKey(taskDefinition.name())
                .active().list());

        final List<String> candidateGroups = candidateGroups(username);
        if (!candidateGroups.isEmpty()) {
            tasks.addAll(taskService.createTaskQuery().taskDefinitionKey(taskDefinition.name())
                    .taskCandidateGroupIn(candidateGroups)
                    .active().list());
        }

        final List<WorkflowRequest> workflowRequests = new ArrayList<>();
        for (Task task : tasks) {
            workflowRequests.add(buildWorkflowRequest(task));
        }
        return workflowRequests;
    }

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username   The assignee username.
     * @param definition The task definition id.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findAssignedByTasks(String username, String definition) {
        Assert.hasText(username, "System cannot complete request. username cannot be null.");
        Assert.hasText(definition, "definition cannot be null or blank.");

        final TaskDefinition taskDefinition = TaskDefinition.valueOf(definition);
        Assert.notNull(taskDefinition, String.format("No task definition found for name %s.", definition));

        final List<WorkflowRequest> tasks = new ArrayList<>();
        for (Task task : taskService.createTaskQuery().taskDefinitionKey(taskDefinition.name()).active().list()) {
            final WorkflowRequest request = (WorkflowRequest) taskService.getVariable(task.getId(), TaskVariables.REQUEST.variableName());
            if (request.getRequestingUser().getUsername().equalsIgnoreCase(username)) {
                tasks.add(request);
            }
        }
        return tasks;
    }

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username    The assignee username.
     * @param definitions The task definition id array.
     * @return A list.
     */
    @Override
    public List<WorkflowRequest> findAssignedByTasks(String username, String[] definitions) {
        Assert.hasText(username, "System cannot complete request. username cannot be null.");
        Assert.notNull(definitions, "definitions cannot be null or blank.");

        final List<WorkflowRequest> tasks = new ArrayList<>();
        for (String definition : definitions) {
            tasks.addAll(findAssignedByTasks(username, definition));
        }
        return tasks;
    }

    /**
     * The method to kick-off the register company process.
     *
     * @param request The request.
     * @return The response.
     */
    @Override
    public RegisterCompanyAccountResponse register(RegisterCompanyAccountRequest request) {
        Assert.notNull(request, "The request cannot be null.");

        final User requestingUser = request.getRequestingUser();
        Assert.notNull(requestingUser, "The requestingUser cannot be null.");

        final String businessKey = request.getCompany().getRegistrationNo();
        LOG.info("businessKey: {}", businessKey);

        request.setBusinessKey(businessKey);
        request.setDateCreated(new Date());

        final Map<String, Object> variables = new HashMap<>();
        variables.put(TaskVariables.REQUEST.variableName(), request);

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CamundaProcess.CompanyRegistration.id()
                , businessKey, variables);

        final RegisterCompanyAccountResponse response = RegisterCompanyAccountResponseBuilder.aRegisterCompanyAccountResponse()
                .withProcessInstanceId(processInstance.getProcessInstanceId())
                .withBusinessKey(businessKey)
                .withRequestingUserId(requestingUser.getPrimaryId())
                .withRequestingUser(requestingUser.getDisplayName())
                .build();
        LOG.info("Company registration process started: " + response.toString());

        return response;
    }

    /**
     * The method to kick-off the register user process.
     *
     * @param request The request.
     * @return The response.
     */
    @Override
    public RegisterUserAccountResponse register(RegisterUserAccountRequest request) {
        Assert.notNull(request, "The request cannot be null.");
        Assert.hasText(request.getSource(), "The source is required.");

        final User requestingUser = request.getRequestingUser();
        Assert.notNull(requestingUser, "The requestingUser cannot be null.");

        final Feature applicationHome = featureService.findByName(Feature.Type.APP_HOME);
        Assert.notNull(applicationHome, "APP_HOME not found.");
        Assert.hasText(applicationHome.getValue(), "APP_HOME not defined.");
        LOG.info("applicationHome: {}", applicationHome.getValue());

        final String businessKey = request.getUser().getPersonalReferenceValue();
        LOG.info("businessKey: {}", businessKey);

        request.setBusinessKey(businessKey);
        request.setDateCreated(new Date());

        // C:\Users\Administrator\.ecoo\temp\<username>
        final String srcDir = FileUtils.resolveDir(applicationHome.getValue()) + "temp"
                + System.getProperty("file.separator") + businessKey;

        final Map<String, Object> variables = new HashMap<>();
        variables.put(TaskVariables.REQUEST.variableName(), request);
        variables.put(UserRegistrationVariables.ADMIN_SOURCE.id()
                , StringUtils.stripToEmpty(request.getSource()).equalsIgnoreCase("ADMIN"));
        variables.put(UserRegistrationVariables.APP_HOME.id(), applicationHome.getValue());
        variables.put(UserRegistrationVariables.SOURCE_DIR.id(), srcDir);

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CamundaProcess.UserRegistration.id()
                , businessKey, variables);

        final RegisterUserAccountResponse response = RegisterUserAccountResponseBuilder.aRegisterUserAccountResponse()
                .withProcessInstanceId(processInstance.getProcessInstanceId())
                .withBusinessKey(businessKey)
                .withRequestingUserId(requestingUser.getPrimaryId())
                .withRequestingUser(requestingUser.getDisplayName())
                .build();
        LOG.info("User registration process started: " + response.toString());

        return response;
    }


    @Override
    public WorkflowRequest findByProcessInstanceId(String processInstanceId) {
        Assert.notNull(processInstanceId, "System cannot complete request. processInstanceId cannot be null.");
        final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId)
                .active()
                .singleResult();
        if (task == null) {
            return null;
        } else {
            return buildWorkflowRequest(task);
        }
    }

    @Override
    public long countAssignedToTasks(String username) {
        Assert.notNull(username, "System cannot complete request. username cannot be null.");

        long count = taskService.createTaskQuery().taskAssignee(username)
                .active()
                .count();

        final List<String> candidateGroups = candidateGroups(username);
        if (!candidateGroups.isEmpty()) {
            count += taskService.createTaskQuery().taskCandidateGroupIn(candidateGroups)
                    .active()
                    .count();
        }
        return count;
    }

    /**
     * Method to approve the given workflow reuqest.
     *
     * @param workflowRequest The workflow request to approve.
     * @return The approved request.
     */
    @Override
    public WorkflowRequest approve(WorkflowRequest workflowRequest) {
        Assert.notNull(workflowRequest, "System cannot complete request. Request cannot be null.");
        Assert.notNull(workflowRequest.getProcessInstanceId(), "System cannot complete request. ProcessInstanceId cannot be null.");

        final Task task = taskService.createTaskQuery().processInstanceId(workflowRequest.getProcessInstanceId()).active()
                .singleResult();
        Assert.notNull(task, String.format("System cannot complete request. No active task found for " +
                "processInstanceId %s.", workflowRequest.getProcessInstanceId()));

        final UserAuthentication authentication = (UserAuthentication) SecurityContextHolder
                .getContext().getAuthentication();
        final User currentUser = (User) authentication.getDetails();
        assertTaskAssignment(workflowRequest.getProcessInstanceId(), task, currentUser);

        final String message = String.format("Process %s approved.", workflowRequest.getProcessInstanceId());
        taskService.createComment(task.getId(), workflowRequest.getProcessInstanceId(), message);

        taskService.complete(task.getId(), createVariables().putValue(TaskVariables.REQUEST.variableName(), workflowRequest)
                .putValue("action", "APPROVE"));

        return workflowRequest;
    }

    @Override
    public TaskCompletedResponse actionTask(TaskActionRequest request) {
        Assert.notNull(request, "System cannot complete request. Request cannot be null.");
        Assert.notNull(request.getProcessInstanceId(), "System cannot complete request. ProcessInstanceId cannot be null.");

        final Task task = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active()
                .singleResult();
        Assert.notNull(task, String.format("System cannot complete request. No active task found for " +
                "processInstanceId %s.", request.getProcessInstanceId()));

        final User requestingUser = userService.findById(request.getRequestingUserId());
        Assert.notNull(requestingUser, String.format("System cannot complete request. No user found for id %s."
                , request.getRequestingUserId()));
        assertTaskAssignment(request.getProcessInstanceId(), task, requestingUser);

        final TaskCompletedResponse response = TaskCompletedResponseBuilder.aTaskCompletedResponse()
                .withProcessInstanceId(request.getProcessInstanceId())
                .withTaskId(task.getId())
                .withTaskName(task.getName())
                .withTaskDefinitionKey(task.getTaskDefinitionKey())
                .withTaskAssignee(task.getAssignee())
                .build();

        final String message = String.format("Task %s: %s", request.getAction(), response.toString());
        LOG.info(message);

        if (StringUtils.isNotBlank(request.getComment())) {
            runtimeService.setVariable(request.getProcessInstanceId(), "comment", request.getComment());

            final WorkflowRequest workflowRequest = (WorkflowRequest) taskService.getVariable(task.getId()
                    , TaskVariables.REQUEST.variableName());
            workflowRequest.setComments(request.getComment());

            taskService.setVariable(task.getId(), TaskVariables.REQUEST.variableName(), workflowRequest);
        }

        taskService.createComment(task.getId(), request.getProcessInstanceId(), message);
        taskService.complete(task.getId(), createVariables().putValue("action", request.getAction())
                .putValue("actionedBy", requestingUser.getPrimaryId()));

        return response;
    }

    private void assertTaskAssignment(String processInstanceId, Task task, User requestingUser) {
        if(requestingUser.isInRole(Role.ROLE_SYSADMIN)) return;
        
        final String taskAssignee = task.getAssignee();
        final String requestingUsername = requestingUser.getUsername();
        if (taskAssignee != null) {
            LOG.info("Task taskAssignee <{}>, requesting user <{}>.", taskAssignee, requestingUsername);
            if (!taskAssignee.equalsIgnoreCase(requestingUsername)) {
                throw new BusinessRuleViolationException(String.format("System cannot action request %s. The user %s <%s> does" +
                                " not have permission to action request as the request is not assigned to you."
                        , processInstanceId, requestingUser.getDisplayName(), requestingUsername));
            }
        } else {
            if (requestingUser.getGroupIdentities().isEmpty()) {
                throw new BusinessRuleViolationException(String.format("System cannot action request %s. The user %s <%s> does" +
                                " not have permission to action request as the request is not assigned to you."
                        , processInstanceId, requestingUser.getDisplayName(), requestingUsername));
            }

            final List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(task.getId());
            final Set<String> groupIds = new HashSet<>();
            for (IdentityLink identityLink : identityLinksForTask) {
                final String groupId = identityLink.getGroupId();
                if (StringUtils.isNotBlank(groupId)) {
                    groupIds.add(groupId);
                }
            }

            LOG.info("Task groups <{}>, requesting user <{}>.", groupIds, requestingUser.getGroupIdentities());
            for (String groupId : groupIds) {
                if (requestingUser.getGroupIdentities().contains(groupId)) {
                    return;
                }
            }
            throw new BusinessRuleViolationException(String.format("System cannot action request %s. The user %s <%s> does" +
                            " not have permission to action request as the request is not assigned to you."
                    , processInstanceId, requestingUser.getDisplayName(), requestingUsername));
        }
    }

    @Override
    public CancelTaskResponse cancelTask(CancelTaskRequest request) {
        Assert.notNull(request, "System cannot complete request. Request cannot be null.");
        Assert.notNull(request.getProcessInstanceId(), "System cannot complete request. ProcessInstanceId cannot be null.");

        final Task task = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active()
                .singleResult();
        if (task == null) {
            return null;
        } else {
            Assert.notNull(task, String.format("System cannot complete request. No active task found for " +
                    "processInstanceId %s.", request.getProcessInstanceId()));

            final User requestingUser = userService.findById(request.getRequestingUserId());
            Assert.notNull(requestingUser, String.format("System cannot complete request. No user found for id %s."
                    , request.getRequestingUserId()));

            final WorkflowRequest workflowRequest = (WorkflowRequest) runtimeService.getVariable(request.getProcessInstanceId()
                    , TaskVariables.REQUEST.variableName());
            // DEVNOTE: Not sure if this is needed.
//            if(!requestingUser.hasRole(Role.ROLE_SYSADMIN) && !Objects.equals(workflowRequest.getRequestingUser(), requestingUser)) {
//                throw new IllegalArgumentException("System cannot cancel task as the requesting user is not the owner of the task.");
//            }

            final String message = String.format("Process %s requested to be cancelled by %s.", request.getProcessInstanceId()
                    , requestingUser.getDisplayName());
            cancelTaskAndUpdate(request.getProcessInstanceId(), workflowRequest, message);

            return new CancelTaskResponse(message, workflowRequest);
        }
    }

    @Transactional
    public void cancelTaskAndUpdate(String processInstanceId, WorkflowRequest workflowRequest, String message) {
        switch (workflowRequest.getType()) {
            case UserRegistration:
                final RegisterUserAccountRequest registerUserAccountRequest = (RegisterUserAccountRequest) workflowRequest;
                final User user = registerUserAccountRequest.getUser();
                userService.delete(user);
                break;
            case NewShipmentRequest:
                final NewShipmentRequest newShipmentRequest = (NewShipmentRequest) workflowRequest;
                final Shipment shipment = newShipmentRequest.getShipment();
                shipment.setProcessInstanceId(null);
                shipment.setStatus(ShipmentStatus.Cancelled.id());
                shipmentService.save(shipment);
                break;
            default:
                throw new UnsupportedOperationException(String.format("Workflow request type %s not supported."
                        , workflowRequest.getType()));
        }
        runtimeService.deleteProcessInstance(processInstanceId, message);
    }


    @Override
    public PasswordResetResponse requestPasswordReset(PasswordResetRequest request) {
        Assert.notNull(request, "The request cannot be null.");

        final User requestingUser = request.getRequestingUser();
        Assert.notNull(requestingUser, "The requestingUser cannot be null.");

        final String businessKey = "PR-" + request.getUser().getPrimaryId();
        LOG.info("businessKey: {}", businessKey);

        request.setBusinessKey(businessKey);
        request.setDateCreated(new Date());

        final Map<String, Object> variables = new HashMap<>();
        variables.put(TaskVariables.REQUEST.variableName(), request);

        final ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(CamundaProcess.PasswordReset.id()
                , businessKey, variables);
        LOG.info("Request reset password process started: " + processInstance.getProcessInstanceId());

        return new PasswordResetResponse(processInstance.getProcessInstanceId());
    }

    private WorkflowRequest buildWorkflowRequest(Task task) {
        final WorkflowRequest request = (WorkflowRequest) taskService.getVariable(task.getId()
                , TaskVariables.REQUEST.variableName());
        if (request.getProcessInstanceId() == null) {
            request.setProcessInstanceId(task.getProcessInstanceId());
        }

        for (IdentityLink identityLink : taskService.getIdentityLinksForTask(task.getId())) {
            final String groupId = identityLink.getGroupId();
            if (StringUtils.isNotBlank(groupId)) {
                request.getCandidateGroups().add(groupId);
            }
        }
        return request;
    }

    private List<String> candidateGroups(String username) {
        final List<String> candidateGroups = new ArrayList<>();

        final User user = userService.findByUsername(username);
        if (user != null) {
            candidateGroups.addAll(user.getAuthorities().stream()
                    .filter(grantedAuthority -> StringUtils.isNotBlank(grantedAuthority.getAuthority()))
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));

            candidateGroups.addAll(user.getGroupIdentities());
        }
        return candidateGroups;
    }
}
