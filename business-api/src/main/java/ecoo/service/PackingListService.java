package ecoo.service;

import ecoo.data.PackingList;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public interface PackingListService extends CrudService<Integer, PackingList>, AuditedModelAware<Integer, PackingList> {

    List<PackingList> findPackingListsByShipmentId(Integer shipmentId);
}
