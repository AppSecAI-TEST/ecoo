
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
 *         &lt;element name="SecurityScope" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}SecurityScopeEnum"/>
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
    "securityScope"
})
@XmlRootElement(name = "ListTasks")
public class ListTasks {

    @XmlElement(name = "SecurityScope", required = true)
    protected SecurityScopeEnum securityScope;

    /**
     * Gets the value of the securityScope property.
     * 
     * @return
     *     possible object is
     *     {@link SecurityScopeEnum }
     *     
     */
    public SecurityScopeEnum getSecurityScope() {
        return securityScope;
    }

    /**
     * Sets the value of the securityScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecurityScopeEnum }
     *     
     */
    public void setSecurityScope(SecurityScopeEnum value) {
        this.securityScope = value;
    }

}
