
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
 *         &lt;element name="StartPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="EndPage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FindValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "startPage",
    "endPage",
    "findValue"
})
@XmlRootElement(name = "FindString")
public class FindString {

    @XmlElement(name = "StartPage")
    protected int startPage;
    @XmlElement(name = "EndPage")
    protected int endPage;
    @XmlElement(name = "FindValue")
    protected String findValue;

    /**
     * Gets the value of the startPage property.
     * 
     */
    public int getStartPage() {
        return startPage;
    }

    /**
     * Sets the value of the startPage property.
     * 
     */
    public void setStartPage(int value) {
        this.startPage = value;
    }

    /**
     * Gets the value of the endPage property.
     * 
     */
    public int getEndPage() {
        return endPage;
    }

    /**
     * Sets the value of the endPage property.
     * 
     */
    public void setEndPage(int value) {
        this.endPage = value;
    }

    /**
     * Gets the value of the findValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFindValue() {
        return findValue;
    }

    /**
     * Sets the value of the findValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFindValue(String value) {
        this.findValue = value;
    }

}
