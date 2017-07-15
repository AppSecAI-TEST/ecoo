package ecoo.bpm.entity;

import ecoo.data.Chamber;
import ecoo.data.Company;
import ecoo.data.User;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public final class RegisterCompanyAccountRequestBuilder {

    private Company company;

    private Chamber chamber;

    private User requestingUser;

    private RegisterCompanyAccountRequestBuilder() {
    }

    public static RegisterCompanyAccountRequestBuilder aRegisterCompanyAccountRequest() {
        return new RegisterCompanyAccountRequestBuilder();
    }

    public RegisterCompanyAccountRequestBuilder withCompany(Company company) {
        this.company = company;
        return this;
    }

    public RegisterCompanyAccountRequestBuilder withChamber(Chamber chamber) {
        this.chamber = chamber;
        return this;
    }

    public RegisterCompanyAccountRequestBuilder withRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
        return this;
    }

    public RegisterCompanyAccountRequest build() {
        RegisterCompanyAccountRequest registerCompanyAccountRequest = new RegisterCompanyAccountRequest();
        registerCompanyAccountRequest.setCompany(company);
        registerCompanyAccountRequest.setChamber(chamber);
        registerCompanyAccountRequest.setRequestingUser(requestingUser);
        return registerCompanyAccountRequest;
    }
}
