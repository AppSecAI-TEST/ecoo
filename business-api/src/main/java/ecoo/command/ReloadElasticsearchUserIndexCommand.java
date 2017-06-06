package ecoo.command;

import ecoo.dao.impl.es.UserElasticsearchIndexLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Justin Rundle
 * @since June 2017
 */
@Component
public class ReloadElasticsearchUserIndexCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ReloadElasticsearchUserIndexCommand.class);

    private UserElasticsearchIndexLoader userElasticsearchIndexLoader;

    @Autowired
    public ReloadElasticsearchUserIndexCommand(UserElasticsearchIndexLoader userElasticsearchIndexLoader) {
        this.userElasticsearchIndexLoader = userElasticsearchIndexLoader;
    }

    public int execute() {
        LOG.info("--- execute ---");
        return userElasticsearchIndexLoader.loadAll().size();
    }
}
