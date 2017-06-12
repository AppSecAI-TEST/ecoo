package ecoo.data;

/**
 * @author Justin Rundle
 * @since June 2017
 */
public enum DocumentTypes {
    CompanyLogo("L"), ProofOfCompanyRegistration("PCR");

    private String primaryId;

    DocumentTypes(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getPrimaryId() {
        return primaryId;
    }
}