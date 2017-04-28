package ecoo.data.upload;


import ecoo.data.BaseModel;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@Entity
@Table(name = "upload_status")
public class UploadStatus extends BaseModel<Integer> {

    private static final long serialVersionUID = -8503783121767687289L;

    public enum Status {

        Running(1, "Running"), UploadFailed(2, "Upload Failed"), UploadSuccessful(3, "Upload Successful"),
        UploadPartial(4, "Upload Partial"), ParsingSuccessful(5, "Parsing Successful"), ParsingPartial(6,
                "Parsing Partial"), ParsingFailed(7, "Parsing Failed"), Ready(8, "Ready"), MarkedAsNew(9,
                "Marked As New"), MarkedAsIgnore(10, "Marked As Ignore"), Exported(11, "Exported"),
        Importing(12, "Importing"), ImportFailed(13, "Import Failed"), Imported(14, "Imported"), Exporting(
                15, "Exporting"), Scheduled(16, "Scheduled"), Queued(17, "Queued");

        private Integer primaryId;
        private String description;

        Status(Integer primaryId, String description) {
            this.primaryId = primaryId;
            this.description = description;
        }

        public Integer getPrimaryId() {
            return primaryId;
        }

        public String getDescription() {
            return description;
        }

        public static Status getStatusByPrimaryId(Integer id) {
            for (Status status : Status.values()) {
                if (status.getPrimaryId().equals(id)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No enum constant " + UploadStatus.Status.class.getCanonicalName() + "." + id);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "descr")
    private String description;


    public UploadStatus(Status status) {
        this.primaryId = status.getPrimaryId();
    }

    public final Status getEnum() {
        return Status.getStatusByPrimaryId(getPrimaryId());
    }

    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }


    public final String getDescription() {
        return description;
    }

    public final void setDescription(String description) {
        this.description = description;
    }
}
