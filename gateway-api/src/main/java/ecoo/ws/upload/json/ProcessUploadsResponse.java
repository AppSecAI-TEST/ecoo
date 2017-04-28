package ecoo.ws.upload.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ecoo.data.upload.Upload;

import java.util.Collection;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class ProcessUploadsResponse {

    private Collection<Upload> queuedUploads;

    private Collection<Upload> scheduledUploads;

    @JsonCreator
    public ProcessUploadsResponse(@JsonProperty("queuedUploads") Collection<Upload> queuedUploads
            , @JsonProperty("massiveUploads") Collection<Upload> scheduledUploads) {
        this.queuedUploads = queuedUploads;
        this.scheduledUploads = scheduledUploads;
    }

    public Collection<Upload> getQueuedUploads() {
        return queuedUploads;
    }

    public Collection<Upload> getScheduledUploads() {
        return scheduledUploads;
    }
}
