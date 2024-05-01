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


/**
 * <p>Java-Klasse für Alignment complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Alignment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="geometry" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="up" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,1" /&gt;
 *       &lt;attribute name="direction" type="{http://www.w3.org/2001/XMLSchema}string" default="0,0,-1" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alignment")
public class Alignment {

    @XmlAttribute(name = "geometry")
    protected String geometry;
    @XmlAttribute(name = "up")
    protected String up;
    @XmlAttribute(name = "direction")
    protected String direction;

    /**
     * Ruft den Wert der geometry-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Legt den Wert der geometry-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeometry(String value) {
        this.geometry = value;
    }

    /**
     * Ruft den Wert der up-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUp() {
        if (up == null) {
            return "0,0,1";
        } else {
            return up;
        }
    }

    /**
     * Legt den Wert der up-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUp(String value) {
        this.up = value;
    }

    /**
     * Ruft den Wert der direction-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        if (direction == null) {
            return "0,0,-1";
        } else {
            return direction;
        }
    }

    /**
     * Legt den Wert der direction-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

}
