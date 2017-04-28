package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.*;
import ecoo.service.ChamberUserService;
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
public class ApproveUserAccountRequestTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(ApproveUserAccountRequestTask.class);

    private CompanyService companyService;

    private UserService userService;

    private ChamberUserService chamberUserService;

    @Autowired
    public ApproveUserAccountRequestTask(CompanyService companyService, UserService userService, ChamberUserService chamberUserService) {
        this.companyService = companyService;
        this.userService = userService;
        this.chamberUserService = chamberUserService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final Integer approvedById = (Integer) delegateExecution.
                getVariable(TaskVariables.ASSIGNEE.variableName());

        final User approvedBy = userService.findById(approvedById);

        final Company company = request.getCompany();
        final Company approvedCompany = approveCompany(company);

        final User user = request.getUser();
        final User approvedUser = approveUser(user, request.getChamber());
        request.setUser(approvedUser);
    }

    private Company approveCompany(Company company) {
        company.setStatus(CompanyStatus.Approved.id());
        companyService.save(company);
        return companyService.findById(company.getPrimaryId());
    }

    private User approveUser(User user, Chamber chamber) {
        user.setStatus(UserStatus.Approved.id());
        userService.save(user);

        ChamberUser chamberUser = chamberUserService.findByChamberAndUser(chamber.getPrimaryId(), user.getPrimaryId());
        if (chamberUser == null) {
            chamberUserService.addAssociation(chamber, user);
        }

        return userService.findById(user.getPrimaryId());
    }
}