package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import ecoo.bpm.common.CamundaProcess;
import ecoo.data.Chamber;
import ecoo.data.Company;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class RegisterCompanyAccountRequest extends WorkflowRequest implements Serializable {

    private static final long serialVersionUID = -2476166425937519749L;

    private Company company;
    
    private Chamber chamber;

    @JsonGetter
    @Override
    public CamundaProcess getType() {
        return CamundaProcess.CompanyRegistration;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Chamber getChamber() {
        return chamber;
    }

    public void setChamber(Chamber chamber) {
        this.chamber = chamber;
    }

    @Override
    public String toString() {
        return "RegisterCompanyAccountRequest{" +
                "company=" + company +
                ", chamber=" + chamber +
                '}';
    }
}