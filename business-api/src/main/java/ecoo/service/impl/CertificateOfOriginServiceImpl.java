package ecoo.service.impl;

import ecoo.dao.CertificateOfOriginDao;
import ecoo.dao.CertificateOfOriginLineDao;
import ecoo.dao.impl.es.CertificateOfOriginElasticsearchRepository;
import ecoo.data.CertificateOfOrigin;
import ecoo.data.CertificateOfOriginLine;
import ecoo.service.CertificateOfOriginService;
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
    public CertificateOfOrigin delete(CertificateOfOriginLine line) {
        Assert.notNull(line, "The variable line cannot be null.");

        final CertificateOfOrigin certificateOfOrigin = findById(line.getParentId());
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
}
