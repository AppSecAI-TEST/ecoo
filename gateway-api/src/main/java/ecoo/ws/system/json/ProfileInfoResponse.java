package ecoo.ws.system.json;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class ProfileInfoResponse {

    private String[] activeProfiles;
    private String ribbonEnv;

    public ProfileInfoResponse(String[] activeProfiles, String ribbonEnv) {
        this.activeProfiles = activeProfiles;
        this.ribbonEnv = ribbonEnv;
    }

    public String[] getActiveProfiles() {
        return activeProfiles;
    }

    public String getRibbonEnv() {
        return ribbonEnv;
    }
}
