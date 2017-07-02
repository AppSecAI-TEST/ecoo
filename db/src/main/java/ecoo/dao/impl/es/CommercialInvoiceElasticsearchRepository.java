package ecoo.dao.impl.es;

import ecoo.data.CommercialInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Repository(value = "commercialInvoiceElasticsearchRepository")
public interface CommercialInvoiceElasticsearchRepository extends ElasticsearchRepository<CommercialInvoice, Integer> {

}
