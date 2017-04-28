
package com.sqlserver.ssrs.reportingservice2005;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfModelCatalogItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfModelCatalogItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ModelCatalogItem" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ModelCatalogItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfModelCatalogItem", propOrder = {
    "modelCatalogItem"
})
public class ArrayOfModelCatalogItem {

    @XmlElement(name = "ModelCatalogItem", nillable = true)
    protected List<ModelCatalogItem> modelCatalogItem;

    /**
     * Gets the value of the modelCatalogItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the modelCatalogItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getModelCatalogItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ModelCatalogItem }
     * 
     * 
     */
    public List<ModelCatalogItem> getModelCatalogItem() {
        if (modelCatalogItem == null) {
            modelCatalogItem = new ArrayList<ModelCatalogItem>();
        }
        return this.modelCatalogItem;
    }

}
