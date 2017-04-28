package ecoo.data;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum UserStatus {
    PendingApproval("PA"), Approved("A"), Declined("D");

    private String id;

    UserStatus(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public static UserStatus valueOfById(String id) {
        for (UserStatus userStatus : values()) {
            if (userStatus.id().equalsIgnoreCase(id)) {
                return userStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant ecoo.data.UserStatus.id: " + id);
    }
}
