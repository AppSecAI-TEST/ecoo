
package com.sqlserver.ssrs.reportingservice2005;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for ItemNamespaceHeader complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemNamespaceHeader">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemNamespace" type="{http://schemas.microsoft.com/sqlserver/2005/06/30/reporting/reportingservices}ItemNamespaceEnum"/>
 *       &lt;/sequence>
 *       &lt;anyAttribute/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemNamespaceHeader", propOrder = {
    "itemNamespace"
})
public class ItemNamespaceHeader {

    @XmlElement(name = "ItemNamespace", required = true)
    protected ItemNamespaceEnum itemNamespace;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the itemNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link ItemNamespaceEnum }
     *     
     */
    public ItemNamespaceEnum getItemNamespace() {
        return itemNamespace;
    }

    /**
     * Sets the value of the itemNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemNamespaceEnum }
     *     
     */
    public void setItemNamespace(ItemNamespaceEnum value) {
        this.itemNamespace = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
