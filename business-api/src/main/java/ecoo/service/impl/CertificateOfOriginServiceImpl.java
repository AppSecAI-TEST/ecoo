package ecoo.service.impl;

import ecoo.dao.CertificateOfOriginDao;
import ecoo.dao.CertificateOfOriginLineDao;
import ecoo.dao.impl.es.CertificateOfOriginElasticsearchRepository;
import ecoo.data.CertificateOfOrigin;
import ecoo.data.CertificateOfOriginLine;
import ecoo.service.CertificateOfOriginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class CertificateOfOriginServiceImpl extends ElasticsearchAuditTemplate<Integer
        , CertificateOfOrigin
        , CertificateOfOriginDao
        , CertificateOfOriginElasticsearchRepository> implements CertificateOfOriginService {

    private CertificateOfOriginLineDao certificateOfOriginLineDao;

    @Autowired
    public CertificateOfOriginServiceImpl(CertificateOfOriginDao dao
            , @Qualifier("certificateOfOriginElasticsearchRepository") CertificateOfOriginElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate
            , CertificateOfOriginLineDao certificateOfOriginLineDao) {
        super(dao, repository, elasticsearchTemplate);
        this.certificateOfOriginLineDao = certificateOfOriginLineDao;
    }

    @Transactional
    @Override
    public CertificateOfOrigin delete(CertificateOfOrigin certificateOfOrigin, CertificateOfOriginLine line) {
        Assert.notNull(certificateOfOrigin, "The variable certificateOfOrigin cannot be null.");
        Assert.notNull(line, "The variable line cannot be null.");

        certificateOfOrigin.getLines().remove(line);

        return save(certificateOfOrigin);
    }

    @Override
    public CertificateOfOriginLine findLineById(Integer id) {
        return certificateOfOriginLineDao.findByPrimaryId(id);
    }

    @Transactional
    @Override
    public CertificateOfOriginLine save(CertificateOfOriginLine line) {
        Assert.notNull(line, "The variable line cannot be null.");
        certificateOfOriginLineDao.save(line);

        final CertificateOfOrigin certificateOfOrigin = findById(line.getParentId());
        reload(certificateOfOrigin);

        return line;
    }

    /**
     * Method called before save is called.
     *
     * @param entity The entity to save.
     */
    @Override
    protected void beforeSave(CertificateOfOrigin entity) {
        entity.setRemarks(StringUtils.stripToNull(entity.getRemarks()));

        // DEVNOTE: In time implement the ability to show the differences changed on the COO.
//        final UserAuthentication authentication = (UserAuthentication) SecurityContextHolder
//                .getContext().getAuthentication();
//        final User currentUser = (User) authentication.getDetails();
//        Assert.notNull(currentUser, "System cannot complete request. No security principle set.");
//
//        shipmentActivityGroupService.recordActivity(currentUser, DateTime.now(), entity.getPrimaryId()
//                , "Certificate of origin amended.");
    }
}
