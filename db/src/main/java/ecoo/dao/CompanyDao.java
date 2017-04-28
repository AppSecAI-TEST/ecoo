package ecoo.dao;

import ecoo.data.Company;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CompanyDao extends AuditLogDao<Integer, Company> {

    Company findByRegistrationNo(String registrationNo);
}
