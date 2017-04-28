
package com.sqlserver.ssrs.reportingservice2005;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExecutionInfo2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecutionInfo2">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ExecutionInfo">
 *       &lt;sequence>
 *         &lt;element name="PageCountMode" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}PageCountMode"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecutionInfo2", propOrder = {
    "pageCountMode"
})
public class ExecutionInfo2
    extends ExecutionInfo
{

    @XmlElement(name = "PageCountMode", required = true)
    protected PageCountMode pageCountMode;

    /**
     * Gets the value of the pageCountMode property.
     * 
     * @return
     *     possible object is
     *     {@link PageCountMode }
     *     
     */
    public PageCountMode getPageCountMode() {
        return pageCountMode;
    }

    /**
     * Sets the value of the pageCountMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageCountMode }
     *     
     */
    public void setPageCountMode(PageCountMode value) {
        this.pageCountMode = value;
    }

}
