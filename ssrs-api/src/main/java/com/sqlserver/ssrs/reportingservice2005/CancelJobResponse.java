
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
 *         &lt;element name="CancelJobResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "cancelJobResult"
})
@XmlRootElement(name = "CancelJobResponse")
public class CancelJobResponse {

    @XmlElement(name = "CancelJobResult")
    protected boolean cancelJobResult;

    /**
     * Gets the value of the cancelJobResult property.
     * 
     */
    public boolean isCancelJobResult() {
        return cancelJobResult;
    }

    /**
     * Sets the value of the cancelJobResult property.
     * 
     */
    public void setCancelJobResult(boolean value) {
        this.cancelJobResult = value;
    }

}
