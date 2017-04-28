package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class PasswordResetResponse implements Serializable {

    private static final long serialVersionUID = 618143884497581393L;

    private String processInstanceId;

    @JsonCreator
    public PasswordResetResponse(@JsonProperty("processInstanceId") String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }
}
