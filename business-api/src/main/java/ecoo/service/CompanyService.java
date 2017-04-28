package ecoo.service;

import ecoo.data.Company;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CompanyService extends CrudService<Integer, Company>, AuditedModelAware<Company> {


}
