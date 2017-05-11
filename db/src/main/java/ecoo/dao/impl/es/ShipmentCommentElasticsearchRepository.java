package ecoo.dao.impl.es;

import ecoo.data.ShipmentComment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Justin Rundle
 * @since May 2017
 */
@Repository(value = "shipmentCommentElasticsearchRepository")
public interface ShipmentCommentElasticsearchRepository extends ElasticsearchRepository<ShipmentComment, Integer> {

    List<ShipmentComment> findShipmentCommentsByShipmentId(Integer shipmentId);
}
