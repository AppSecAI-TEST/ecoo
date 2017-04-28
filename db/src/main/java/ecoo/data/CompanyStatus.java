package ecoo.data;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum CompanyStatus {
    PendingApproval("PA"), Approved("A"), Declined("D");

    private String id;

    CompanyStatus(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static CompanyStatus valueOfById(String id) {
        for (CompanyStatus userStatus : values()) {
            if (userStatus.id().equalsIgnoreCase(id)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant ecoo.data.CompanyStatus.id: " + id);
    }
}
