package ecoo.dao;


import ecoo.data.upload.RequiredFieldMappingItem;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface RequiredFieldMappingItemDao extends BaseDao<Integer, RequiredFieldMappingItem> {

    /**
     * Returns all upload mapping details linked to the given upload mapping
     *
     * @param uploadMappingId The mapping pk.
     * @return {@link RequiredFieldMappingItem}
     */
    Collection<RequiredFieldMappingItem> findByUploadMapping(Integer uploadMappingId);
}
