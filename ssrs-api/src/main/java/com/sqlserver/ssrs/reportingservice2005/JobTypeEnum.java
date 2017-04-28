
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="JobTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="User"/>
 *     &lt;enumeration value="System"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "JobTypeEnum")
@XmlEnum
public enum JobTypeEnum {

    @XmlEnumValue("User")
    USER("User"),
    @XmlEnumValue("System")
    SYSTEM("System");
    private final String value;

    JobTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static JobTypeEnum fromValue(String v) {
        for (JobTypeEnum c: JobTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
