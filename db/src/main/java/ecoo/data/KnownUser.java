package ecoo.data;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public enum KnownUser {
    Anonymous(-99), SystemAccount(-98), BatchProcessorAccount(-97);

    private Integer primaryId;

    KnownUser(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }
}