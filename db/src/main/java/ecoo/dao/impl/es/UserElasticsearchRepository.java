package ecoo.dao.impl.es;

import ecoo.data.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Repository(value = "userElasticsearchRepository")
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, Integer> {

    List<User> findUsersByPrimaryEmailAddress(String primaryEmailAddress);

    List<User> findUsersByMobileNumber(String mobileNumber);

    List<User> findUsersByUsername(String username);

    List<User> findUsersByStatus(String status);

    List<User> findUsersByPersonalReferenceTypeAndPersonalReferenceValue(String personalReferenceType, String personalReferenceValue);
}
