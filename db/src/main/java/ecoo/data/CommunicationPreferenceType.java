package ecoo.data;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum CommunicationPreferenceType {
    SMS("S"), EMAIL("E");

    private String id;

    CommunicationPreferenceType(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }
}
