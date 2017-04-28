
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WeekNumberEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WeekNumberEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FirstWeek"/>
 *     &lt;enumeration value="SecondWeek"/>
 *     &lt;enumeration value="ThirdWeek"/>
 *     &lt;enumeration value="FourthWeek"/>
 *     &lt;enumeration value="LastWeek"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WeekNumberEnum")
@XmlEnum
public enum WeekNumberEnum {

    @XmlEnumValue("FirstWeek")
    FIRST_WEEK("FirstWeek"),
    @XmlEnumValue("SecondWeek")
    SECOND_WEEK("SecondWeek"),
    @XmlEnumValue("ThirdWeek")
    THIRD_WEEK("ThirdWeek"),
    @XmlEnumValue("FourthWeek")
    FOURTH_WEEK("FourthWeek"),
    @XmlEnumValue("LastWeek")
    LAST_WEEK("LastWeek");
    private final String value;

    WeekNumberEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WeekNumberEnum fromValue(String v) {
        for (WeekNumberEnum c: WeekNumberEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
