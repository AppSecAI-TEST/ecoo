
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="JobStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="New"/>
 *     &lt;enumeration value="Running"/>
 *     &lt;enumeration value="CancelRequested"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "JobStatusEnum")
@XmlEnum
public enum JobStatusEnum {

    @XmlEnumValue("New")
    NEW("New"),
    @XmlEnumValue("Running")
    RUNNING("Running"),
    @XmlEnumValue("CancelRequested")
    CANCEL_REQUESTED("CancelRequested");
    private final String value;

    JobStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static JobStatusEnum fromValue(String v) {
        for (JobStatusEnum c: JobStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
