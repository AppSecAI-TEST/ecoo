
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ItemTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Folder"/>
 *     &lt;enumeration value="Report"/>
 *     &lt;enumeration value="Resource"/>
 *     &lt;enumeration value="LinkedReport"/>
 *     &lt;enumeration value="DataSource"/>
 *     &lt;enumeration value="Model"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ItemTypeEnum")
@XmlEnum
public enum ItemTypeEnum {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Folder")
    FOLDER("Folder"),
    @XmlEnumValue("Report")
    REPORT("Report"),
    @XmlEnumValue("Resource")
    RESOURCE("Resource"),
    @XmlEnumValue("LinkedReport")
    LINKED_REPORT("LinkedReport"),
    @XmlEnumValue("DataSource")
    DATA_SOURCE("DataSource"),
    @XmlEnumValue("Model")
    MODEL("Model");
    private final String value;

    ItemTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ItemTypeEnum fromValue(String v) {
        for (ItemTypeEnum c: ItemTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
