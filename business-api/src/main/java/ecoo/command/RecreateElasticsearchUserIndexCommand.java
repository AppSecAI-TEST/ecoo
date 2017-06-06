package ecoo.command;

import ecoo.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Component
public class RecreateElasticsearchUserIndexCommand {

    private static final Logger LOG = LoggerFactory.getLogger(RecreateElasticsearchUserIndexCommand.class);

    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    public RecreateElasticsearchUserIndexCommand(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public boolean execute() {
        LOG.info("--- execute ---");
        if (elasticsearchTemplate.indexExists(User.class)) {
            elasticsearchTemplate.deleteIndex(User.class);
        }
        return elasticsearchTemplate.createIndex(User.class);
    }
}
