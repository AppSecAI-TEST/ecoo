package ecoo.bpm.tasks.user.registration;

import ecoo.bpm.constants.TaskVariables;
import ecoo.bpm.entity.RegisterUserAccountRequest;
import ecoo.data.Company;
import ecoo.data.CompanyStatus;
import ecoo.service.CompanyService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@SuppressWarnings("unused")
@Component
public class MarkCompanyAsPendingDocumentationTask implements JavaDelegate {

    private final Logger log = LoggerFactory.getLogger(MarkCompanyAsPendingDocumentationTask.class);

    private CompanyService companyService;

    @Autowired
    public MarkCompanyAsPendingDocumentationTask(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void execute(final DelegateExecution delegateExecution) throws Exception {
        log.info("Called");

        final RegisterUserAccountRequest request = (RegisterUserAccountRequest) delegateExecution.
                getVariable(TaskVariables.REQUEST.variableName());
        request.setProcessInstanceId(delegateExecution.getProcessInstanceId());

        final Company company = request.getCompany();
        company.setStatus(CompanyStatus.PendingDocumentation.id());

        log.info("Attempting to save company... {}", company.toString());
        companyService.save(company);

        request.getUser().setCompany(company);
    }
}