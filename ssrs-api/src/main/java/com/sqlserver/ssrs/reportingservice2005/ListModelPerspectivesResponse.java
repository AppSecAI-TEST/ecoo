
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
 *         &lt;element name="ModelCatalogItems" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ArrayOfModelCatalogItem" minOccurs="0"/>
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
    "modelCatalogItems"
})
@XmlRootElement(name = "ListModelPerspectivesResponse")
public class ListModelPerspectivesResponse {

    @XmlElement(name = "ModelCatalogItems")
    protected ArrayOfModelCatalogItem modelCatalogItems;

    /**
     * Gets the value of the modelCatalogItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfModelCatalogItem }
     *     
     */
    public ArrayOfModelCatalogItem getModelCatalogItems() {
        return modelCatalogItems;
    }

    /**
     * Sets the value of the modelCatalogItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfModelCatalogItem }
     *     
     */
    public void setModelCatalogItems(ArrayOfModelCatalogItem value) {
        this.modelCatalogItems = value;
    }

}
