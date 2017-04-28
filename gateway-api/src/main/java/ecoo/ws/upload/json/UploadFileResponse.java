package ecoo.ws.upload.json;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class UploadFileResponse {

    private String fileName;

    private String mimeType;

    private long sizeInKb;

    public UploadFileResponse(String fileName, String mimeType, long sizeInKb) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.sizeInKb = sizeInKb;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getSizeInKb() {
        return sizeInKb;
    }

    @Override
    public String toString() {
        return "UploadFileResponse{" +
                "fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", sizeInKb=" + sizeInKb +
                '}';
    }
}
