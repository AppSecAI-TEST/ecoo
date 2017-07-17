package ecoo.data;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public enum ShipmentStatus {
    NewAndPendingSubmission("N"), SubmittedAndPendingChamberApproval("SPA")
    , Approved("APP"), Declined("D"), Cancelled("C");

    private String id;

    ShipmentStatus(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static ShipmentStatus valueOfById(String id) {
        for (ShipmentStatus userStatus : values()) {
            if (userStatus.id().equalsIgnoreCase(id)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant ecoo.data.ShipmentStatus.id: " + id);
    }
}
