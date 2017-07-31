package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.constants.UserRegistrationVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.User;
import ecoo.data.UserStatus;
import ecoo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class InitializeUserRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(InitializeUserRequestTask.class);

    private UserService userService;

    @Autowired
    public InitializeUserRequestTask(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());
        request.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        final User user = request.getUser();
        user.setDisplayName(StringUtils.upperCase(user.getFirstName() + " " + user.getLastName()));

        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setReserved(false);
        user.setPasswordExpired(false);

        final String activationSerialNumber = UUID.randomUUID().toString().replace("-", "");
        user.setActivationSerialNumber(activationSerialNumber);
        user.setLastLoginTime(null);

        final String plainTextPassword = request.getPlainTextPassword();
        userService.setPassword(user, plainTextPassword, false);
        delegateExecution.setVariable(UserRegistrationVariables.PLAIN_TEXT_PASSWORD.id(), plainTextPassword);

        // Default status "Pending Documentation".
        user.setStatus(UserStatus.PendingDocumentation.id());

        log.info("Attempting to save user... {}", user.toString());
        userService.save(user);
    }
}