package ecoo.service.impl;

import ecoo.dao.PackingListDao;
import ecoo.dao.impl.es.PackingListElasticsearchRepository;
import ecoo.data.PackingList;
import ecoo.service.PackingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Service
public class PackListServiceImpl extends JdbcElasticsearchAuditTemplate<Integer
        , PackingList
        , PackingListDao
        , PackingListElasticsearchRepository> implements PackingListService {

    @Autowired
    public PackListServiceImpl(PackingListDao dao
            , @Qualifier("packingListElasticsearchRepository") PackingListElasticsearchRepository repository
            , ElasticsearchTemplate elasticsearchTemplate) {
        super(dao, repository, elasticsearchTemplate, PackingList.class);
    }
}
