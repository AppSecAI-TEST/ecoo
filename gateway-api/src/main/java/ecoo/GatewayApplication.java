package ecoo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@ComponentScan(basePackages = "ecoo.ws.user.rest")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class
        , HibernateJpaAutoConfiguration.class
        , SolrAutoConfiguration.class
        , DataSourceTransactionManagerAutoConfiguration.class
        , ElasticsearchAutoConfiguration.class
        , ElasticsearchDataAutoConfiguration.class
        , ElasticsearchRepositoriesAutoConfiguration.class
        , JmsAutoConfiguration.class})
@EnableScheduling
public class GatewayApplication {

    private final static Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Inject
    private Environment env;

    /**
     * Initializes.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(GatewayApplication.class
                , "classpath:spring/ecoo-ws-context.xml");
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);

        final Runtime runtime = Runtime.getRuntime();
        final NumberFormat format = NumberFormat.getInstance();
        final long maxMemory = runtime.maxMemory();
        final long allocatedMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();
        final long mb = 1024 * 1024;
        final String mega = " MB";
        log.info("=============================== RUNNING =========================");
        log.info("Free memory: " + format.format(freeMemory / mb) + mega);
        log.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
        log.info("Max memory: " + format.format(maxMemory / mb) + mega);
        log.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
        log.info("=================================================================\n");
    }
}
