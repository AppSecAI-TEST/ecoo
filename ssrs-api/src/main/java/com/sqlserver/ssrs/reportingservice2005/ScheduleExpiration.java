
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScheduleExpiration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScheduleExpiration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ExpirationDefinition">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="ScheduleReference" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ScheduleReference" minOccurs="0"/>
 *           &lt;element name="ScheduleDefinition" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ScheduleDefinition" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScheduleExpiration", propOrder = {
    "scheduleReference",
    "scheduleDefinition"
})
public class ScheduleExpiration
    extends ExpirationDefinition
{

    @XmlElement(name = "ScheduleReference")
    protected ScheduleReference scheduleReference;
    @XmlElement(name = "ScheduleDefinition")
    protected ScheduleDefinition scheduleDefinition;

    /**
     * Gets the value of the scheduleReference property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleReference }
     *     
     */
    public ScheduleReference getScheduleReference() {
        return scheduleReference;
    }

    /**
     * Sets the value of the scheduleReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleReference }
     *     
     */
    public void setScheduleReference(ScheduleReference value) {
        this.scheduleReference = value;
    }

    /**
     * Gets the value of the scheduleDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduleDefinition }
     *     
     */
    public ScheduleDefinition getScheduleDefinition() {
        return scheduleDefinition;
    }

    /**
     * Sets the value of the scheduleDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduleDefinition }
     *     
     */
    public void setScheduleDefinition(ScheduleDefinition value) {
        this.scheduleDefinition = value;
    }

}
