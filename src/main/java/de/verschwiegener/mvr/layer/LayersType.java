//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//


package de.verschwiegener.mvr.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für LayersType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LayersType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Layer" type="{}LayerType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LayersType", propOrder = {
    "layers"
})
public class LayersType {

    @XmlElement(name = "Layer", required = true)
    protected List<LayerType> layers;

    /**
     * Ruft den Wert der layer-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link LayerType }
     *     
     */
    public List<LayerType> getContent() {
        if (layers == null) {
        	layers = new ArrayList<LayerType>();
        }
        return this.layers;
    }
    
    public LayerType getLayerByName(String name) {
    	return getContent().stream().filter(layer -> layer.getName().equals(name)).findFirst().orElse(null);
    }
    
    public LayerType getLayerByUUID(String uuid) {
    	return getContent().stream().filter(layer -> layer.getUuid().equals(uuid)).findFirst().orElse(null);
    }

}
