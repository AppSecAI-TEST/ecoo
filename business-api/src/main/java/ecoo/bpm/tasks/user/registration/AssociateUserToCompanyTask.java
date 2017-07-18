package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.Company;
import ecoo.data.User;
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
public class AssociateUserToCompanyTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(AssociateUserToCompanyTask.class);

    private CompanyService companyService;

    private UserService userService;

    @Autowired
    public AssociateUserToCompanyTask(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final User user = request.getUser();

        final Company company = companyService.findById(request.getCompany().getPrimaryId());
        user.setCompany(company);
        request.setCompany(company);

        userService.save(user);
        request.setUser(user);
    }
}
