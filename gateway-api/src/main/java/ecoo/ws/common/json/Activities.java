package ecoo.ws.common.json;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class Activities {

    private String lastUpdated;

    private Collection<ActivityType> types = new ArrayList<>();

    public Activities(String lastUpdated, Collection<ActivityType> types) {
        this.lastUpdated = lastUpdated;
        this.types = types;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Collection<ActivityType> getTypes() {
        return types;
    }

    public void setTypes(Collection<ActivityType> types) {
        this.types = types;
    }
}
