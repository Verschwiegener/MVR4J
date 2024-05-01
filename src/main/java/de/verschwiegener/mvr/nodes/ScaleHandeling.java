//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.nodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.nodes.enums.Scaleenum;


/**
 * <p>Java-Klasse für ScaleHandeling complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ScaleHandeling"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Enum" type="{}scaleenum" default="ScaleKeepRatio" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScaleHandeling")
public class ScaleHandeling {

    @XmlAttribute(name = "Enum")
    protected Scaleenum _enum;

    /**
     * Ruft den Wert der enum-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Scaleenum }
     *     
     */
    public Scaleenum getEnum() {
        if (_enum == null) {
            return Scaleenum.SCALE_KEEP_RATIO;
        } else {
            return _enum;
        }
    }

    /**
     * Legt den Wert der enum-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Scaleenum }
     *     
     */
    public void setEnum(Scaleenum value) {
        this._enum = value;
    }

}
