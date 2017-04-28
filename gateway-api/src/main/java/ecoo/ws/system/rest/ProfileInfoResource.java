package ecoo.ws.system.rest;

import ecoo.ws.common.rest.BaseResource;
import ecoo.ws.system.json.ProfileInfoResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@RestController
@RequestMapping("/api/profile-info")
public class ProfileInfoResource extends BaseResource {

    private static final String[] displayOnActiveProfiles = {"dev", "qa"};

    @Inject
    private Environment env;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProfileInfoResponse getActiveProfiles() {
        return new ProfileInfoResponse(env.getActiveProfiles(), getRibbonEnv());
    }

    private String getRibbonEnv() {
        final String[] activeProfiles = env.getActiveProfiles();
        final List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
        final List<String> springBootProfiles = Arrays.asList(activeProfiles);
        ribbonProfiles.retainAll(springBootProfiles);

        // Get first item either "dev" or "qa".
        if (ribbonProfiles.size() > 0) {
            return ribbonProfiles.get(0);
        }
        return null;
    }
}
