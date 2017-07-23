package ecoo.service;

import ecoo.data.ChamberAdmin;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public interface ChamberAdminService extends CrudService<Integer, ChamberAdmin>, AuditedModelAware<Integer, ChamberAdmin> {

    Collection<ChamberAdmin> findByUser(Integer userId);

}
