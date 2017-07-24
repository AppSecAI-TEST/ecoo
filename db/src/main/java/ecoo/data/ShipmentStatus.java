package ecoo.data;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public enum ShipmentStatus {
    NewAndPendingSubmission("N", "New And Pending Submission"), SubmittedAndPendingChamberApproval("SPA", "Submitted And Pending Chamber Approval"), Approved("APP", "Approved"), Declined("D", "Declined"), Cancelled("C", "Cancelled");

    private String id;

    private String description;

    ShipmentStatus(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String id() {
        return id;
    }

    public String description() {
        return description;
    }

    public static ShipmentStatus valueOfById(String id) {
        for (ShipmentStatus userStatus : values()) {
            if (userStatus.id().equalsIgnoreCase(id)) {
                return userStatus;
            }
        }
        return null;
    }
}
