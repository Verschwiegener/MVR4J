//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//


package de.verschwiegener.mvr.auxData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.layer.AUXDataType;


/**
 * <p>Java-Klasse für SceneType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SceneType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Layers" type="{}LayersType"/&gt;
 *         &lt;element name="AUXData" type="{}AUXDataType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SceneType", propOrder = {
    "layers",
    "auxData"
})
public class SceneType {

    @XmlElement(name = "Layers", required = true)
    protected LayersType layers;
    @XmlElement(name = "AUXData", required = true)
    protected AUXDataType auxData;

    /**
     * Ruft den Wert der layers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LayersType }
     *     
     */
    public LayersType getLayers() {
        return layers;
    }

    /**
     * Legt den Wert der layers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link LayersType }
     *     
     */
    public void setLayers(LayersType value) {
        this.layers = value;
    }

    /**
     * Ruft den Wert der auxData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AUXDataType }
     *     
     */
    public AUXDataType getAUXData() {
        return auxData;
    }

    /**
     * Legt den Wert der auxData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AUXDataType }
     *     
     */
    public void setAUXData(AUXDataType value) {
        this.auxData = value;
    }

}
