
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModelItemTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ModelItemTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Model"/>
 *     &lt;enumeration value="EntityFolder"/>
 *     &lt;enumeration value="FieldFolder"/>
 *     &lt;enumeration value="Entity"/>
 *     &lt;enumeration value="Attribute"/>
 *     &lt;enumeration value="Role"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ModelItemTypeEnum")
@XmlEnum
public enum ModelItemTypeEnum {

    @XmlEnumValue("Model")
    MODEL("Model"),
    @XmlEnumValue("EntityFolder")
    ENTITY_FOLDER("EntityFolder"),
    @XmlEnumValue("FieldFolder")
    FIELD_FOLDER("FieldFolder"),
    @XmlEnumValue("Entity")
    ENTITY("Entity"),
    @XmlEnumValue("Attribute")
    ATTRIBUTE("Attribute"),
    @XmlEnumValue("Role")
    ROLE("Role");
    private final String value;

    ModelItemTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ModelItemTypeEnum fromValue(String v) {
        for (ModelItemTypeEnum c: ModelItemTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
