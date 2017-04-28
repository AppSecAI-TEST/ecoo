
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Report" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExecutionSetting" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ExecutionSettingEnum"/>
 *         &lt;choice>
 *           &lt;element name="NoSchedule" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}NoSchedule" minOccurs="0"/>
 *           &lt;element name="ScheduleDefinition" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ScheduleDefinition" minOccurs="0"/>
 *           &lt;element name="ScheduleReference" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ScheduleReference" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "report",
    "executionSetting",
    "noSchedule",
    "scheduleDefinition",
    "scheduleReference"
})
@XmlRootElement(name = "SetExecutionOptions")
public class SetExecutionOptions {

    @XmlElement(name = "Report")
    protected String report;
    @XmlElement(name = "ExecutionSetting", required = true)
    protected ExecutionSettingEnum executionSetting;
    @XmlElement(name = "NoSchedule")
    protected NoSchedule noSchedule;
    @XmlElement(name = "ScheduleDefinition")
    protected ScheduleDefinition scheduleDefinition;
    @XmlElement(name = "ScheduleReference")
    protected ScheduleReference scheduleReference;

    /**
     * Gets the value of the report property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReport() {
        return report;
    }

    /**
     * Sets the value of the report property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReport(String value) {
        this.report = value;
    }

    /**
     * Gets the value of the executionSetting property.
     * 
     * @return
     *     possible object is
     *     {@link ExecutionSettingEnum }
     *     
     */
    public ExecutionSettingEnum getExecutionSetting() {
        return executionSetting;
    }

    /**
     * Sets the value of the executionSetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExecutionSettingEnum }
     *     
     */
    public void setExecutionSetting(ExecutionSettingEnum value) {
        this.executionSetting = value;
    }

    /**
     * Gets the value of the noSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link NoSchedule }
     *     
     */
    public NoSchedule getNoSchedule() {
        return noSchedule;
    }

    /**
     * Sets the value of the noSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoSchedule }
     *     
     */
    public void setNoSchedule(NoSchedule value) {
        this.noSchedule = value;
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

}
