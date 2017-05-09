package ecoo.bpm.common;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum CamundaProcess {

    /**
     * A new user request.
     */
    UserRegistration("UserRegistration", "fa-user"),

    /**
     * A create invoice request.
     */
    PasswordReset("PasswordReset", "fa-unlock-alt"),

    /**
     * A new shipment request.
     */
    ShipmentRequest("ShipmentRequest", "fa-file-text-o");

    private String id;

    private String icon;

    CamundaProcess(String id, String icon) {
        this.id = id;
        this.icon = icon;
    }

    public String id() {
        return id;
    }

    public String icon() {
        return icon;
    }
}
