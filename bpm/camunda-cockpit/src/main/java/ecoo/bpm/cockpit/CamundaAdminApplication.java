package ecoo.bpm.cockpit;

import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.spring.boot.starter.SpringBootProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://localhost:2222/camunda-cockpit/app/admin/default/setup/#/setup
 *
 * @author Justin Rundle
 * @since July 2017
 */
@SpringBootApplication
@ProcessApplication
public class CamundaAdminApplication extends SpringBootProcessApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamundaAdminApplication.class, args);
    }
}

