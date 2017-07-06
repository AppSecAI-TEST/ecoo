package ecoo.data.upload;

import ecoo.data.BaseModel;

import javax.persistence.*;

/**
 * @author Justin Rundle
 * @since April 2017
 */
@MappedSuperclass
public abstract class UploadData extends BaseModel<Integer> {

    private static final long serialVersionUID = 2388786592803369143L;

    public static int PROPERTY_COMMENT_LENGTH = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer primaryId;

    @Column(name = "upload_id")
    private Integer uploadId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "comments")
    private String comments;

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#getPrimaryId()
     */
    @Override
    public final Integer getPrimaryId() {
        return primaryId;
    }

    /*
     * (non-Javadoc)
     *
     * @see za.co.aforbes.fpc.db.model.BaseModel#setPrimaryId(java.lang.Object)
     */
    @Override
    public final void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * @return the uploadId
     */
    public final Integer getUploadId() {
        return uploadId;
    }

    /**
     * @param uploadId the uploadId to set
     */
    public final void setUploadId(Integer uploadId) {
        this.uploadId = uploadId;
    }

    /**
     * @return the status
     */
    public final Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public final void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
