package ecoo.ws.upload.json;


import ecoo.data.upload.PreviewCsvFile;
import ecoo.data.upload.RequiredFieldMapping;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class NewUploadRequest {

    private String uploadTypeId;

    private PreviewCsvFile previewCsvFile;

    private RequiredFieldMapping requiredField;

    public NewUploadRequest() {
    }

    public String getUploadTypeId() {
        return uploadTypeId;
    }

    public void setUploadTypeId(String uploadTypeId) {
        this.uploadTypeId = uploadTypeId;
    }

    public PreviewCsvFile getPreviewCsvFile() {
        return previewCsvFile;
    }

    public void setPreviewCsvFile(PreviewCsvFile previewCsvFile) {
        this.previewCsvFile = previewCsvFile;
    }

    public RequiredFieldMapping getRequiredField() {
        return requiredField;
    }

    public void setRequiredField(RequiredFieldMapping requiredField) {
        this.requiredField = requiredField;
    }

    @Override
    public String toString() {
        return "NewUploadRequest{" +
                "uploadTypeId=" + uploadTypeId +
                ", previewCsvFile=" + previewCsvFile +
                ", requiredField=" + requiredField +
                '}';
    }
}
