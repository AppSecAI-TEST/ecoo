
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MinuteRecurrence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MinuteRecurrence">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}RecurrencePattern">
 *       &lt;sequence>
 *         &lt;element name="MinutesInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MinuteRecurrence", propOrder = {
    "minutesInterval"
})
public class MinuteRecurrence
    extends RecurrencePattern
{

    @XmlElement(name = "MinutesInterval")
    protected int minutesInterval;

    /**
     * Gets the value of the minutesInterval property.
     * 
     */
    public int getMinutesInterval() {
        return minutesInterval;
    }

    /**
     * Sets the value of the minutesInterval property.
     * 
     */
    public void setMinutesInterval(int value) {
        this.minutesInterval = value;
    }

}
