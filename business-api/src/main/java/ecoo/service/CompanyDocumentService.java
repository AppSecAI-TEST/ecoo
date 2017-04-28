package ecoo.service;

import ecoo.data.CompanyDocument;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface CompanyDocumentService extends CrudService<Integer, CompanyDocument>, AuditedModelAware<CompanyDocument> {


}
