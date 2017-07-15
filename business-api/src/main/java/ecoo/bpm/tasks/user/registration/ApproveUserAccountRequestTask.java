package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.*;
import ecoo.service.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

    private SignatureService signatureService;

    private UserSignatureService userSignatureService;

    private CompanySignatoryService companySignatoryService;

    @Autowired
    public ApproveUserAccountRequestTask(CompanyService companyService, UserService userService, ChamberUserService chamberUserService, SignatureService signatureService, UserSignatureService userSignatureService, CompanySignatoryService companySignatoryService) {
        this.companyService = companyService;
        this.userService = userService;
        this.chamberUserService = chamberUserService;
        this.signatureService = signatureService;
        this.userSignatureService = userSignatureService;
        this.companySignatoryService = companySignatoryService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());

        final User user = request.getUser();
        final Signature signature = request.getSignature();
        Assert.notNull(signature, String.format("System cannot complete request. No signature assigned " +
                "to user %s.", request.getUser().getPrimaryId()));

        approveSignature(signature, user);

        final Integer approvedById = (Integer) delegateExecution.getVariable(TaskVariables.ASSIGNEE.variableName());

        final User approvedBy = userService.findById(approvedById);

        final Company company = companyService.findById(request.getCompany().getPrimaryId());
        final Company approvedCompany = approveCompanySignatory(user, company);

        final User approvedUser = approveUser(user, company, request.getChamber(), request.getMember().equalsIgnoreCase("Y"));
        request.setUser(approvedUser);
    }

    private Company approveCompanySignatory(User user, Company company) {
        companySignatoryService.addSignatory(user, company.getPrimaryId());
        return company;
    }

    private User approveUser(User user, Company company, Chamber chamber, boolean member) {
        user.setCompany(company);
        user.setStatus(UserStatus.Approved.id());
        userService.save(user);

        ChamberUser chamberUser = chamberUserService.findByChamberAndUser(chamber.getPrimaryId(), user.getPrimaryId());
        if (chamberUser == null) {
            chamberUserService.addAssociation(chamber, user, member);
        }

        return userService.findById(user.getPrimaryId());
    }

    private void approveSignature(Signature signature, User user) {
        UserSignature userSignature = new UserSignature();
        userSignature.setUserId(user.getPrimaryId());
        userSignature.setEncodedImage(signature.getEncodedImage());
        userSignature.setEffectiveFrom(DateTime.now().withTimeAtStartOfDay().toDate());
        userSignature.setEffectiveTo(DateTime.parse("99991231", DateTimeFormat.forPattern("yyyyMMdd")).toDate());

        userSignature = userSignatureService.save(userSignature);
        log.info("Saving... {}", userSignature);

        signature.setUserSignatureId(userSignature.getPrimaryId());
        signatureService.save(signature);
        log.info("Saving... {}", signature);
    }
}