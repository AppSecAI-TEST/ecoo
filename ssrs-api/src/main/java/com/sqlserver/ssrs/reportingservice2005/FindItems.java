
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
 *         &lt;element name="Folder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BooleanOperator" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}BooleanOperatorEnum"/>
 *         &lt;element name="Conditions" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ArrayOfSearchCondition" minOccurs="0"/>
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
    "folder",
    "booleanOperator",
    "conditions"
})
@XmlRootElement(name = "FindItems")
public class FindItems {

    @XmlElement(name = "Folder")
    protected String folder;
    @XmlElement(name = "BooleanOperator", required = true)
    protected BooleanOperatorEnum booleanOperator;
    @XmlElement(name = "Conditions")
    protected ArrayOfSearchCondition conditions;

    /**
     * Gets the value of the folder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolder() {
        return folder;
    }

    /**
     * Sets the value of the folder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolder(String value) {
        this.folder = value;
    }

    /**
     * Gets the value of the booleanOperator property.
     * 
     * @return
     *     possible object is
     *     {@link BooleanOperatorEnum }
     *     
     */
    public BooleanOperatorEnum getBooleanOperator() {
        return booleanOperator;
    }

    /**
     * Sets the value of the booleanOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link BooleanOperatorEnum }
     *     
     */
    public void setBooleanOperator(BooleanOperatorEnum value) {
        this.booleanOperator = value;
    }

    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSearchCondition }
     *     
     */
    public ArrayOfSearchCondition getConditions() {
        return conditions;
    }

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSearchCondition }
     *     
     */
    public void setConditions(ArrayOfSearchCondition value) {
        this.conditions = value;
    }

}
