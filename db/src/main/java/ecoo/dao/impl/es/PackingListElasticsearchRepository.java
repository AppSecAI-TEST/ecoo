package ecoo.dao.impl.es;

import ecoo.data.PackingList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Repository(value = "packingListElasticsearchRepository")
public interface PackingListElasticsearchRepository extends ElasticsearchRepository<PackingList, Integer> {

}
