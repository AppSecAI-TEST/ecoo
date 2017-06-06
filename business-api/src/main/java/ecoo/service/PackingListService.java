package ecoo.service;

import ecoo.data.PackingList;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface PackingListService extends CrudService<Integer, PackingList>, AuditedModelAware<PackingList> {

}
