package ecoo.dao;


import ecoo.data.upload.RequiredFieldMapping;
import ecoo.data.upload.UploadType;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public interface RequiredFieldMappingDao extends BaseDao<Integer, RequiredFieldMapping> {

    /**
     * Returns the default mapping for the give import type.
     *
     * @param type The import type.
     * @return The default field mapping or null if none found.
     */
    RequiredFieldMapping findDefaultRequiredFieldMappingByType(UploadType.Type type);

    /**
     * Returns all upload mappings linked to the given upload type
     *
     * @param type The upload type.
     * @return {@link RequiredFieldMapping}
     */
    Collection<RequiredFieldMapping> findRequiredFieldMappingByType(UploadType.Type type);

    /**
     * checks if the given uploadMapping name has been used to create another mapping
     *
     * @param type        The upload type.
     * @param mappingName The mapping name.
     * @return true if there exists an uploadMapping that has the same upload mappingName
     */
    boolean isUploadMappingNameDuplicate(UploadType.Type type, String mappingName);
}
