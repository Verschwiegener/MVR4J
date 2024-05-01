//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.layer.AUXData;
import de.verschwiegener.mvr.layer.Layers;


/**
 * <p>Java-Klasse für Scene complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Scene"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Layers" type="{}Layers"/&gt;
 *         &lt;element name="AUXData" type="{}AUXData" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Scene", propOrder = {
    "layers",
    "auxData"
})
public class Scene {

    @XmlElement(name = "Layers", required = true)
    protected Layers layers;
    @XmlElement(name = "AUXData")
    protected AUXData auxData;

    /**
     * Ruft den Wert der layers-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Layers }
     *     
     */
    public Layers getLayers() {
        return layers;
    }

    /**
     * Legt den Wert der layers-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Layers }
     *     
     */
    public void setLayers(Layers value) {
        this.layers = value;
    }

    /**
     * Ruft den Wert der auxData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link AUXData }
     *     
     */
    public AUXData getAUXData() {
        return auxData;
    }

    /**
     * Legt den Wert der auxData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link AUXData }
     *     
     */
    public void setAUXData(AUXData value) {
        this.auxData = value;
    }

}
