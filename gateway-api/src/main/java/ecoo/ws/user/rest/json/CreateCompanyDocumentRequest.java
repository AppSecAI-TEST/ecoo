package ecoo.ws.user.rest.json;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class CreateCompanyDocumentRequest {

    private Integer primaryId;

    private Integer companyId;

    private String documentType;

    private String fileName;

    private String mimeType;

    private long sizeInKb;

    public Integer getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getSizeInKb() {
        return sizeInKb;
    }

    public void setSizeInKb(long sizeInKb) {
        this.sizeInKb = sizeInKb;
    }

    @Override
    public String toString() {
        return "CreateCompanyDocumentRequest{" +
                "primaryId=" + primaryId +
                ", companyId=" + companyId +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInKb=" + sizeInKb +
                '}';
    }
}
