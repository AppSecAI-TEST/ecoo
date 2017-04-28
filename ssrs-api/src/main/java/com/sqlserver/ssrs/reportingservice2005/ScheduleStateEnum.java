
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScheduleStateEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ScheduleStateEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Ready"/>
 *     &lt;enumeration value="Running"/>
 *     &lt;enumeration value="Paused"/>
 *     &lt;enumeration value="Expired"/>
 *     &lt;enumeration value="Failing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ScheduleStateEnum")
@XmlEnum
public enum ScheduleStateEnum {

    @XmlEnumValue("Ready")
    READY("Ready"),
    @XmlEnumValue("Running")
    RUNNING("Running"),
    @XmlEnumValue("Paused")
    PAUSED("Paused"),
    @XmlEnumValue("Expired")
    EXPIRED("Expired"),
    @XmlEnumValue("Failing")
    FAILING("Failing");
    private final String value;

    ScheduleStateEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ScheduleStateEnum fromValue(String v) {
        for (ScheduleStateEnum c: ScheduleStateEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
