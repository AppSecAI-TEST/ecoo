package ecoo.service.impl;

import ecoo.dao.CertificateOfOriginDao;
import ecoo.dao.impl.es.CertificateOfOriginElasticsearchRepository;
import ecoo.data.CertificateOfOrigin;
import ecoo.service.CertificateOfOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class CertificateOfOriginServiceImpl extends ElasticsearchAuditTemplate<Integer
        , CertificateOfOrigin
        , CertificateOfOriginDao
        , CertificateOfOriginElasticsearchRepository> implements CertificateOfOriginService {

    private CertificateOfOriginElasticsearchRepository repository;

    @Autowired
    public CertificateOfOriginServiceImpl(CertificateOfOriginDao dao
            , @Qualifier("certificateOfOriginElasticsearchRepository") CertificateOfOriginElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate);
        this.repository = repository;
    }

    @Override
    public List<CertificateOfOrigin> findCertificateOfOriginsByShipmentId(Integer shipmentId) {
        Assert.notNull(shipmentId, "The variable shipmentId cannot be null.");
        return repository.findCertificateOfOriginsByShipmentId(shipmentId);
    }
}
