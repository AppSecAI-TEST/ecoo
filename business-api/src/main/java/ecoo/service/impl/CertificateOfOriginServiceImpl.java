package ecoo.service.impl;

import ecoo.dao.CertificateOfOriginDao;
import ecoo.dao.impl.es.CertificateOfOriginElasticsearchRepository;
import ecoo.data.CertificateOfOrigin;
import ecoo.service.CertificateOfOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class CertificateOfOriginServiceImpl extends JdbcElasticsearchAuditTemplate<Integer
        , CertificateOfOrigin
        , CertificateOfOriginDao
        , CertificateOfOriginElasticsearchRepository> implements CertificateOfOriginService {

    @Autowired
    public CertificateOfOriginServiceImpl(CertificateOfOriginDao dao
            , @Qualifier("certificateOfOriginElasticsearchRepository") CertificateOfOriginElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate, CertificateOfOrigin.class);
    }
}
