
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DailyRecurrence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DailyRecurrence">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}RecurrencePattern">
 *       &lt;sequence>
 *         &lt;element name="DaysInterval" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DailyRecurrence", propOrder = {
    "daysInterval"
})
public class DailyRecurrence
    extends RecurrencePattern
{

    @XmlElement(name = "DaysInterval")
    protected int daysInterval;

    /**
     * Gets the value of the daysInterval property.
     * 
     */
    public int getDaysInterval() {
        return daysInterval;
    }

    /**
     * Sets the value of the daysInterval property.
     * 
     */
    public void setDaysInterval(int value) {
        this.daysInterval = value;
    }

}
