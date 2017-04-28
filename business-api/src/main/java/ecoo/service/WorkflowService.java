package ecoo.service;


import ecoo.bpm.common.TaskDefinition;
import ecoo.bpm.entity.*;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface WorkflowService {

    /**
     * Method to request a password reset.
     *
     * @param request The reset password request.
     * @return The reset password response.
     */
    PasswordResetResponse requestPasswordReset(PasswordResetRequest request);

    /**
     * Returns the request for the unique process instance id.
     *
     * @param processInstanceId The process instance id.
     * @return The request or null.
     */
    WorkflowRequest findByProcessInstanceId(String processInstanceId);

    /**
     * Returns the active tasks for the given type.
     *
     * @param taskDefinition The task definition.
     * @return A list.
     */
    List<WorkflowRequest> findActiveTasksByTaskDefinition(TaskDefinition taskDefinition);

    /**
     * Returns the active tasks for the given type.
     *
     * @param taskDefinition The task definition.
     * @return A list.
     */
    List<WorkflowRequest> findActiveTasksByTaskDefinition(String taskDefinition);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username The assignee username.
     * @return A list.
     */
    List<WorkflowRequest> findAssignedToTasks(String username);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username       The assignee username.
     * @param taskDefinition The task definition id.
     * @return A list.
     */
    List<WorkflowRequest> findAssignedToTasks(String username, String taskDefinition);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username        The assignee username.
     * @param taskDefinitions The task definition id array.
     * @return A list.
     */
    List<WorkflowRequest> findAssignedToTasks(String username, String[] taskDefinitions);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username       The assignee username.
     * @param taskDefinition The task definition id.
     * @return A list.
     */
    List<WorkflowRequest> findAssignedByTasks(String username, String taskDefinition);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username        The assignee username.
     * @param taskDefinitions The task definition id array.
     * @return A list.
     */
    List<WorkflowRequest> findAssignedByTasks(String username, String[] taskDefinitions);

    /**
     * Returns count of all unverified user account tasks.
     *
     * @param username The assignee username.
     * @return The count.
     */
    long countAssignedToTasks(String username);

    /**
     * The method to kick-off the register user process.
     *
     * @param request The request.
     * @return The response.
     */
    RegisterUserAccountResponse register(RegisterUserAccountRequest request);

    /**
     * Method to act on a task and do something.
     *
     * @param request The task action request.
     * @return The task completed action response.
     */
    TaskCompletedResponse actionTask(TaskActionRequest request);

    /**
     * Method to cancel a task.
     *
     * @param request The cancel task request.
     * @return The task cancelled response.
     */
    CancelTaskResponse cancelTask(CancelTaskRequest request);
}
