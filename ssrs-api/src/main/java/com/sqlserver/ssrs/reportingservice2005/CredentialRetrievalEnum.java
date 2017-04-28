
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CredentialRetrievalEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CredentialRetrievalEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Prompt"/>
 *     &lt;enumeration value="Store"/>
 *     &lt;enumeration value="Integrated"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CredentialRetrievalEnum")
@XmlEnum
public enum CredentialRetrievalEnum {

    @XmlEnumValue("Prompt")
    PROMPT("Prompt"),
    @XmlEnumValue("Store")
    STORE("Store"),
    @XmlEnumValue("Integrated")
    INTEGRATED("Integrated"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    CredentialRetrievalEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CredentialRetrievalEnum fromValue(String v) {
        for (CredentialRetrievalEnum c: CredentialRetrievalEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
