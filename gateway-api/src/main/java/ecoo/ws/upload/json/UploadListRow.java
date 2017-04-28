package ecoo.ws.upload.json;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since March 2016
 */
public class UploadListRow {

    private Integer primaryId;

    private String fileName;

    private String type;

    private Integer statusId;

    private String status;

    private Date startTime;

    private String processingTime;

    private String comment;

    public UploadListRow(Integer primaryId, String fileName, String type, Integer statusId, String status, Date startTime, String processingTime, String comment) {
        this.primaryId = primaryId;
        this.fileName = fileName;
        this.type = type;
        this.statusId = statusId;
        this.status = status;
        this.startTime = startTime;
        this.processingTime = processingTime;
        this.comment = comment;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadListRow that = (UploadListRow) o;

        return primaryId.equals(that.primaryId);

    }

    @Override
    public int hashCode() {
        return primaryId.hashCode();
    }
}
