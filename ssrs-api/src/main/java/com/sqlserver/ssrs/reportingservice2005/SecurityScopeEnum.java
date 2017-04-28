
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SecurityScopeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SecurityScopeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="System"/>
 *     &lt;enumeration value="Catalog"/>
 *     &lt;enumeration value="Model"/>
 *     &lt;enumeration value="All"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SecurityScopeEnum")
@XmlEnum
public enum SecurityScopeEnum {

    @XmlEnumValue("System")
    SYSTEM("System"),
    @XmlEnumValue("Catalog")
    CATALOG("Catalog"),
    @XmlEnumValue("Model")
    MODEL("Model"),
    @XmlEnumValue("All")
    ALL("All");
    private final String value;

    SecurityScopeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SecurityScopeEnum fromValue(String v) {
        for (SecurityScopeEnum c: SecurityScopeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
