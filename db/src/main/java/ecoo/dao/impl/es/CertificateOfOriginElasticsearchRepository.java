package ecoo.dao.impl.es;

import ecoo.data.CertificateOfOrigin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Repository(value = "certificateOfOriginElasticsearchRepository")
public interface CertificateOfOriginElasticsearchRepository extends ElasticsearchRepository<CertificateOfOrigin, Integer> {

}
