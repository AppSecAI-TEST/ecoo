package ecoo.dao.impl.hibernate;

import ecoo.dao.RequiredFieldMappingDao;
import ecoo.data.upload.RequiredFieldMapping;
import ecoo.data.upload.UploadType;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@SuppressWarnings("unchecked")
@Repository(value = "requiredFieldMappingDao")
public class RequiredFieldMappingDaoImpl extends BaseHibernateDaoImpl<Integer, RequiredFieldMapping> implements RequiredFieldMappingDao {

    /**
     * Constructs a new {@link RequiredFieldMappingDaoImpl} data access object.
     */
    @Autowired
    public RequiredFieldMappingDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, RequiredFieldMapping.class);
    }

    /**
     * Returns the default mapping for the give import type.
     *
     * @param type The import type.
     * @return The default field mapping or null if none found.
     */
    @Override
    public RequiredFieldMapping findDefaultRequiredFieldMappingByType(UploadType.Type type) {
        Assert.notNull(type);

        String sql = "select distinct mapping" +
                " from " + RequiredFieldMapping.class.getSimpleName() + " mapping" +
                " where mapping.uploadTypeId = ?" +
                " and mapping.name = ?";

        String defaultName = "default-" + type.name().toLowerCase();

        final Collection<RequiredFieldMapping> data = (Collection<RequiredFieldMapping>) getHibernateTemplate().find(sql,
                type.getPrimaryId(), defaultName);
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data.iterator().next();
    }

    /**
     * Returns all upload mappings linked to the given upload type
     *
     * @param type The upload type.
     * @return {@link RequiredFieldMapping}
     */
    @Override
    public Collection<RequiredFieldMapping> findRequiredFieldMappingByType(UploadType.Type type) {
        Assert.notNull(type);

        String sql = "select distinct mapping" +
                " from " + RequiredFieldMapping.class.getSimpleName() + " mapping" +
                " where mapping.uploadTypeId = ?";

        return (Collection<RequiredFieldMapping>) getHibernateTemplate().find(sql, type.getPrimaryId());
    }

    /**
     * checks if the given uploadMapping name has been used to create another mapping
     *
     * @param type        The upload type.
     * @param mappingName The mapping name.
     * @return true if there exists an uploadMapping that has the same upload mappingName
     */
    @Override
    public boolean isUploadMappingNameDuplicate(UploadType.Type type, String mappingName) {
        Assert.notNull(type);
        Assert.hasText(mappingName);

        String sql = "select count(uploadmapping)" +
                " from " + RequiredFieldMapping.class.getSimpleName() + " uploadmapping" +
                " where uploadmapping.uploadTypeId = ?" +
                " and uploadmapping.name = ?";

        final Collection<Long> data = (Collection<Long>) getHibernateTemplate().find(sql,
                type.getPrimaryId(), StringUtils.stripToEmpty(mappingName));
        if (data == null || data.isEmpty()) {
            return false;
        }
        return data.iterator().next() > 0L;
    }
}
