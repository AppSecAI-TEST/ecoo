package ecoo.ws.common.rest;

import ecoo.bpm.common.TaskDefinition;
import ecoo.bpm.entity.*;
import ecoo.service.WorkflowService;
import ecoo.ws.common.json.Activities;
import ecoo.ws.common.json.ActivityType;
import ecoo.ws.common.json.TaskListRow;
import ecoo.ws.common.json.TaskListRowBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/bpm/tasks")
public class WorkflowResource extends BaseResource {

    private WorkflowService workflowService;

    @Autowired
    public WorkflowResource(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @RequestMapping(value = "/active/definition/{definition}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TaskListRow>> findActiveTasksByTaskDefinition(@PathVariable String definition) {
        Assert.hasText(definition, "The variable definition cannot be null or blank.");

        final TaskDefinition taskDefinition = TaskDefinition.valueOf(definition);

        final Collection<TaskListRow> myTasks = new ArrayList<>();
        myTasks.addAll(workflowService.findActiveTasksByTaskDefinition(taskDefinition)
                .stream().map(request -> TaskListRowBuilder.aTaskListRow()
                        .withWorkflowRequest(request)
                        .withTaskId(Integer.parseInt(request.getProcessInstanceId()))
                        .withTaskType(request.getType())
                        .withDescription(WorkflowRequestDescriptionBuilder.aDescription()
                                .withWorkflowRequest(request)
                                .build())
                        .withDateCreated(request.getDateCreated())
                        .build()).collect(Collectors.toCollection(ArrayList::new)));
        return ResponseEntity.ok(myTasks);
    }

    @RequestMapping(value = "/processInstanceId/{processInstanceId}", method = RequestMethod.GET
            , headers = "Accept=application/json"
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<WorkflowRequest> findByProcessInstanceId(@PathVariable String processInstanceId) throws Exception {
        Assert.notNull(processInstanceId, "System cannot complete request. processInstanceId cannot be null.");
        return ResponseEntity.ok(workflowService.findByProcessInstanceId(processInstanceId));
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<WorkflowRequest> approve(@RequestBody WorkflowRequest workflowRequest) {
        Assert.notNull(workflowRequest, "System cannot complete request. Request cannot be null.");
        return ResponseEntity.ok(workflowService.approve(workflowRequest));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TaskCompletedResponse> actionTask(@RequestBody TaskActionRequest request) {
        Assert.notNull(request, "System cannot complete request. Request cannot be null.");
        return ResponseEntity.ok(workflowService.actionTask(request));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CancelTaskResponse> cancelTask(@RequestBody CancelTaskRequest request) {
        Assert.notNull(request, "System cannot complete request. Request cannot be null.");
        return ResponseEntity.ok(workflowService.cancelTask(request));
    }

    @RequestMapping(value = "/activities/assignedTo/{username:.+}", method = RequestMethod.GET)
    public ResponseEntity<Activities> activities(@PathVariable String username) {
        Assert.hasText(username, "username cannot be null.");

        final Collection<ActivityType> types = new ArrayList<>();
        types.add(new ActivityType("Tasks", "tasks", workflowService.countAssignedToTasks(username)));

        // DEVNOTE: Maybe in time put this back when needed.
        //types.add(new ActivityType("Created Tasks", "createdTasks", tasks.get(4)));
        return ResponseEntity.ok(new Activities(DateTime.now().toString("yyyy/MM/dd HH:mm"), types));
    }

    @RequestMapping(value = "/assignedTo/{username:.+}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TaskListRow>> assignedToTasks(@PathVariable String username) {
        Assert.notNull(username, "username cannot be null.");

        final Collection<TaskListRow> myTasks = workflowService.findAssignedToTasks(username)
                .stream().map(request -> TaskListRowBuilder.aTaskListRow()
                        .withWorkflowRequest(request)
                        .withTaskId(Integer.parseInt(request.getProcessInstanceId()))
                        .withTaskType(request.getType())
                        .withDescription(WorkflowRequestDescriptionBuilder.aDescription()
                                .withWorkflowRequest(request)
                                .build())
                        .withDateCreated(request.getDateCreated())
                        .build()).collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok(myTasks);
    }

    @RequestMapping(value = "/processInstanceId/{processInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<TaskListRow> findTaskByProcessInstanceId(@PathVariable String processInstanceId) {
        Assert.hasText(processInstanceId, "The variable processInstanceId cannot be null.");

        final WorkflowRequest request = workflowService.findByProcessInstanceId(processInstanceId);
        return ResponseEntity.ok(TaskListRowBuilder.aTaskListRow()
                .withWorkflowRequest(request)
                .withTaskId(Integer.parseInt(request.getProcessInstanceId()))
                .withTaskType(request.getType())
                .withDescription(WorkflowRequestDescriptionBuilder.aDescription()
                        .withWorkflowRequest(request)
                        .build())
                .withDateCreated(request.getDateCreated())
                .build());
    }

    @RequestMapping(value = "/processType/UserRegistration/user/{username:.+}", method = RequestMethod.GET)
    public ResponseEntity<Collection<TaskListRow>> myPendingUserRegistrationTasks(@PathVariable String username) {
        Assert.hasText(username, "username cannot be null or blank.");

        final Collection<TaskListRow> myTasks = new ArrayList<>();
        myTasks.addAll(workflowService.findAssignedToTasks(username, TaskDefinition.VerifyUserAccountRequest.name())
                .stream().map(request -> TaskListRowBuilder.aTaskListRow()
                        .withWorkflowRequest(request)
                        .withTaskId(Integer.parseInt(request.getProcessInstanceId()))
                        .withTaskType(request.getType())
                        .withDescription(WorkflowRequestDescriptionBuilder.aDescription()
                                .withWorkflowRequest(request)
                                .build())
                        .withDateCreated(request.getDateCreated())
                        .build()).collect(Collectors.toCollection(ArrayList::new)));
        return ResponseEntity.ok(myTasks);
    }
}