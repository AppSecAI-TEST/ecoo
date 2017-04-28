
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExecutionSettingEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExecutionSettingEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Live"/>
 *     &lt;enumeration value="Snapshot"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExecutionSettingEnum")
@XmlEnum
public enum ExecutionSettingEnum {

    @XmlEnumValue("Live")
    LIVE("Live"),
    @XmlEnumValue("Snapshot")
    SNAPSHOT("Snapshot");
    private final String value;

    ExecutionSettingEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExecutionSettingEnum fromValue(String v) {
        for (ExecutionSettingEnum c: ExecutionSettingEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
