package ecoo.bpm.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Justin Rundle
 * @since May 2017
 */
public class CreateSignatureBpmResponse implements Serializable {


    private String processInstanceId;

    @JsonCreator
    public CreateSignatureBpmResponse(@JsonProperty("processInstanceId") String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public String toString() {
        return "CreateSignatureBpmResponse{" +
                ", processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}