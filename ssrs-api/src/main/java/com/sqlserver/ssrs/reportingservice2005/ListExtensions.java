
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
 *         &lt;element name="ExtensionType" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ExtensionTypeEnum"/>
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
    "extensionType"
})
@XmlRootElement(name = "ListExtensions")
public class ListExtensions {

    @XmlElement(name = "ExtensionType", required = true)
    protected ExtensionTypeEnum extensionType;

    /**
     * Gets the value of the extensionType property.
     * 
     * @return
     *     possible object is
     *     {@link ExtensionTypeEnum }
     *     
     */
    public ExtensionTypeEnum getExtensionType() {
        return extensionType;
    }

    /**
     * Sets the value of the extensionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtensionTypeEnum }
     *     
     */
    public void setExtensionType(ExtensionTypeEnum value) {
        this.extensionType = value;
    }

}
