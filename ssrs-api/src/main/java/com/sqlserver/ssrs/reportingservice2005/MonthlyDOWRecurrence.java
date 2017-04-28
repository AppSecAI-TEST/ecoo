
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MonthlyDOWRecurrence complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MonthlyDOWRecurrence">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}RecurrencePattern">
 *       &lt;sequence>
 *         &lt;element name="WhichWeek" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}WeekNumberEnum" minOccurs="0"/>
 *         &lt;element name="DaysOfWeek" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}DaysOfWeekSelector" minOccurs="0"/>
 *         &lt;element name="MonthsOfYear" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}MonthsOfYearSelector" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MonthlyDOWRecurrence", propOrder = {
    "whichWeek",
    "daysOfWeek",
    "monthsOfYear"
})
public class MonthlyDOWRecurrence
    extends RecurrencePattern
{

    @XmlElement(name = "WhichWeek")
    protected WeekNumberEnum whichWeek;
    @XmlElement(name = "DaysOfWeek")
    protected DaysOfWeekSelector daysOfWeek;
    @XmlElement(name = "MonthsOfYear")
    protected MonthsOfYearSelector monthsOfYear;

    /**
     * Gets the value of the whichWeek property.
     * 
     * @return
     *     possible object is
     *     {@link WeekNumberEnum }
     *     
     */
    public WeekNumberEnum getWhichWeek() {
        return whichWeek;
    }

    /**
     * Sets the value of the whichWeek property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeekNumberEnum }
     *     
     */
    public void setWhichWeek(WeekNumberEnum value) {
        this.whichWeek = value;
    }

    /**
     * Gets the value of the daysOfWeek property.
     * 
     * @return
     *     possible object is
     *     {@link DaysOfWeekSelector }
     *     
     */
    public DaysOfWeekSelector getDaysOfWeek() {
        return daysOfWeek;
    }

    /**
     * Sets the value of the daysOfWeek property.
     * 
     * @param value
     *     allowed object is
     *     {@link DaysOfWeekSelector }
     *     
     */
    public void setDaysOfWeek(DaysOfWeekSelector value) {
        this.daysOfWeek = value;
    }

    /**
     * Gets the value of the monthsOfYear property.
     * 
     * @return
     *     possible object is
     *     {@link MonthsOfYearSelector }
     *     
     */
    public MonthsOfYearSelector getMonthsOfYear() {
        return monthsOfYear;
    }

    /**
     * Sets the value of the monthsOfYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthsOfYearSelector }
     *     
     */
    public void setMonthsOfYear(MonthsOfYearSelector value) {
        this.monthsOfYear = value;
    }

}
