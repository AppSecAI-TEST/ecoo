package ecoo.ws.user.rest;

import ecoo.bpm.entity.*;
import ecoo.data.*;
import ecoo.data.audit.Revision;
import ecoo.security.UserAuthentication;
import ecoo.service.UserService;
import ecoo.service.WorkflowService;
import ecoo.validator.CompanyValidator;
import ecoo.validator.UserValidator;
import ecoo.ws.common.rest.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/users")
public class UserResource extends BaseResource {

    private UserService userService;

    private WorkflowService workflowService;

    private UserValidator userValidator;

    private CompanyValidator companyValidator;

    @Autowired
    public UserResource(UserService userService, WorkflowService workflowService, UserValidator userValidator, CompanyValidator companyValidator) {
        this.userService = userService;
        this.workflowService = workflowService;
        this.userValidator = userValidator;
        this.companyValidator = companyValidator;
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<ForgotPasswordResponse> requestPasswordReset(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(workflowService.forgetPassword(request));
    }

    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findUsersByCompany(@PathVariable Integer companyId) {
        Assert.notNull(companyId, "The variable companyId cannot be null.");
        return ResponseEntity.ok(userService.findUsersByCompany(companyId));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<RegisterUserAccountResponse> register(@RequestBody RegisterUserAccountRequest request) {
        Assert.notNull(request, "The variable request cannot be null.");
        Assert.hasText(request.getSource(), "The source cannot be null.");

        if (request.getSource().equalsIgnoreCase("PUBLIC")) {
            final User systemAccount = userService.findById(KnownUser.SystemAccount.getPrimaryId());
            SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(systemAccount));
        }

        companyValidator.validate(request.getCompany());
        userValidator.validate(request.getUser());

        if (!request.getCompany().isInStatus(CompanyStatus.Approved)) {
            final RegisterCompanyAccountRequest registerCompanyAccountRequest = RegisterCompanyAccountRequestBuilder.aRegisterCompanyAccountRequest()
                    .withChamber(request.getChamber())
                    .withCompany(request.getCompany())
                    .withRequestingUser(request.getRequestingUser())
                    .build();
            workflowService.register(registerCompanyAccountRequest);
        }
        return ResponseEntity.ok(workflowService.register(request));
    }


    @RequestMapping(value = "/register/validate", method = RequestMethod.POST)
    public ResponseEntity<RegisterUserAccountRequest> validate(@RequestBody RegisterUserAccountRequest registerUserAccountRequest) {
        registerUserAccountRequest.setComments(null);

        try {
            companyValidator.validate(registerUserAccountRequest.getCompany());
            userValidator.validate(registerUserAccountRequest.getUser());

        } catch (final Exception e) {
            registerUserAccountRequest.setComments(e.getMessage());
        }
        return ResponseEntity.ok(registerUserAccountRequest);
    }

    @RequestMapping(value = "/role/{roleName}", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findByRole(@PathVariable String roleName) {
        final Role role = Role.valueOf(roleName);
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @RequestMapping(value = "/lock", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> disableAccount(@RequestBody User user) {
        Assert.notNull(user);
        return ResponseEntity.ok(userService.disableAccount(user));
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> enableAccount(@RequestBody User user) {
        Assert.notNull(user);
        return ResponseEntity.ok(userService.enableAccount(user));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @RequestMapping(value = "/associatedTo/me", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findUsersAssociatedToMe() {
        return ResponseEntity.ok(userService.findUsersAssociatedToMe(currentUser()));
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
    public ResponseEntity<Collection<User>> findByStatus(@PathVariable String status) {
        return ResponseEntity.ok(userService.findByStatus(UserStatus.valueOfById(status)));
    }

    @RequestMapping(value = "/status/{status}/size", method = RequestMethod.GET)
    public ResponseEntity<Long> countByStatus(@PathVariable String status) {
        return ResponseEntity.ok(userService.countByStatus(UserStatus.valueOfById(status)));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
        userValidator.validate(user);
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@RequestBody User user) {
        return ResponseEntity.ok(userService.delete(user));
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<PasswordResetResponse> requestPasswordReset(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(workflowService.requestPasswordReset(request));
    }

//    @RequestMapping(value = "/download/processInstanceId/{processInstanceId}", method = RequestMethod.GET)
//    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String processInstanceId) throws IOException {
//        Assert.notNull(processInstanceId, "processInstanceId is required.");
//
//        final RegisterUserAccountRequest registerUserAccountRequest = (RegisterUserAccountRequest) workflowService
//                .findByProcessInstanceId(processInstanceId);
//        Assert.notNull(registerUserAccountRequest, "No active verification task found for process id " + processInstanceId + ".");
//
//        final ServletContext context = request.getServletContext();
//        final String zipFileName = registerUserAccountRequest.getZipFileName();
//
//        final DownloadFileCommand downloadFileCommand = new DownloadFileCommand();
//        downloadFileCommand.execute(response, context, zipFileName);
//    }

    @RequestMapping(value = "/createdBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findCreatedBy(@PathVariable Integer id) {
        final User entity = userService.findById(id);
        return ResponseEntity.ok(userService.findCreatedBy(entity));
    }

    @RequestMapping(value = "/modifiedBy/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Revision> findModifiedBy(@PathVariable Integer id) {
        final User entity = userService.findById(id);
        return ResponseEntity.ok(userService.findModifiedBy(entity));
    }
}