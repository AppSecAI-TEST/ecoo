package ecoo.ws.common.json;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class ActivityType {

    private String title;

    private String name;

    private long length;

    public ActivityType(String title, String name, long length) {
        this.title = title;
        this.name = name;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "ActivityType{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
