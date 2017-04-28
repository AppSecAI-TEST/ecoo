
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtensionTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ExtensionTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Delivery"/>
 *     &lt;enumeration value="Render"/>
 *     &lt;enumeration value="Data"/>
 *     &lt;enumeration value="All"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ExtensionTypeEnum")
@XmlEnum
public enum ExtensionTypeEnum {

    @XmlEnumValue("Delivery")
    DELIVERY("Delivery"),
    @XmlEnumValue("Render")
    RENDER("Render"),
    @XmlEnumValue("Data")
    DATA("Data"),
    @XmlEnumValue("All")
    ALL("All");
    private final String value;

    ExtensionTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ExtensionTypeEnum fromValue(String v) {
        for (ExtensionTypeEnum c: ExtensionTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
