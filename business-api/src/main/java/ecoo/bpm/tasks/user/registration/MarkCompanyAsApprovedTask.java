package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterCompanyAccountRequest;
import ecoo.data.Company;
import ecoo.data.CompanyStatus;
import ecoo.data.User;
import ecoo.service.CompanyService;
import ecoo.service.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class MarkCompanyAsApprovedTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(MarkCompanyAsApprovedTask.class);

    private CompanyService companyService;

    private UserService userService;

    @Autowired
    public MarkCompanyAsApprovedTask(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterCompanyAccountRequest request = (RegisterCompanyAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());
        request.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        final Company company = request.getCompany();
        company.setStatus(CompanyStatus.Approved.id());

        log.info("Attempting to save company... {}", company.toString());
        companyService.save(company);

        updateCompanyReference(company);
    }

    private void updateCompanyReference(Company company) {
        final List<User> users = userService.findUsersByCompany(company.getPrimaryId());
        log.info("Found {} users to update company reference.", users.size());

        for (User user : users) {
            user.setCompany(company);
            userService.save(user);
        }
    }
}