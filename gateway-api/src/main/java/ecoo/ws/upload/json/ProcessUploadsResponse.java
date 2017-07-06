package ecoo.ws.upload.json;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since July 2017
 */
public class ProcessUploadsResponse {

    private Collection<Integer> uploadIds;

    public ProcessUploadsResponse(Collection<Integer> uploadIds) {
        this.uploadIds = uploadIds;
    }

    public Collection<Integer> getUploadIds() {
        return uploadIds;
    }

    public void setUploadIds(Collection<Integer> uploadIds) {
        this.uploadIds = uploadIds;
    }
}
