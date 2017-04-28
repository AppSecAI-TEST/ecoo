package ecoo.dao.impl.hibernate;

import ecoo.dao.RequiredFieldMappingItemDao;
import ecoo.data.upload.RequiredFieldMappingItem;
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
@Repository(value = "requiredFieldMappingItemDao")
public class RequiredFieldMappingItemDaoImpl extends BaseHibernateDaoImpl<Integer, RequiredFieldMappingItem> implements RequiredFieldMappingItemDao {

    /**
     * Constructs a new {@link RequiredFieldMappingItemDaoImpl} data access object.
     */
    @Autowired
    public RequiredFieldMappingItemDaoImpl(@Qualifier("spivSessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory, RequiredFieldMappingItem.class);
    }

    /**
     * Returns all upload mapping details linked to the given upload mapping
     *
     * @param uploadMappingId The mapping pk.
     * @return {@link RequiredFieldMappingItem}
     */
    @SuppressWarnings({"unchecked", "JpaQlInspection"})
    @Override
    public Collection<RequiredFieldMappingItem> findByUploadMapping(Integer uploadMappingId) {
        Assert.notNull(uploadMappingId, "The variable uploadMappingId cannot be null.");

        String sql = "FROM UploadMappingDetail AS umd" +
                " WHERE umd.mappingId = '" + uploadMappingId + "'";

        return (Collection<RequiredFieldMappingItem>) getHibernateTemplate().find(sql);
    }
}
