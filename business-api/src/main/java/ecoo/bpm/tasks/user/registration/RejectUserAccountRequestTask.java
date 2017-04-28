package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.Company;
import ecoo.data.CompanyStatus;
import ecoo.data.User;
import ecoo.data.UserStatus;
import ecoo.service.CompanyService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unused")
@Component
public class RejectUserAccountRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(RejectUserAccountRequestTask.class);

    private CompanyService companyService;

    private UserService userService;

    @Autowired
    public RejectUserAccountRequestTask(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        declineCompany(request.getCompany());
        declineUser(request.getUser());
    }


    private Company declineCompany(Company company) {
        company.setStatus(CompanyStatus.Declined.id());
        companyService.save(company);
        return companyService.findById(company.getPrimaryId());
    }


    private User declineUser(User user) {
        user.setStatus(UserStatus.Declined.id());
        userService.save(user);
        return userService.findById(user.getPrimaryId());
    }
}