//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.nodes;

import java.math.BigInteger;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für MappingDefinition complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MappingDefinition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SizeX" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="SizeY" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="Source" type="{}Source"/&gt;
 *         &lt;element name="ScaleHandeling" type="{}ScaleHandeling" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="uuid" use="required" type="{}guidtype" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MappingDefinition", propOrder = {
    "sizeX",
    "sizeY",
    "source",
    "scaleHandeling"
})
public class MappingDefinition {

    @XmlElement(name = "SizeX", required = true)
    protected BigInteger sizeX;
    @XmlElement(name = "SizeY", required = true)
    protected BigInteger sizeY;
    @XmlElement(name = "Source", required = true)
    protected Source source;
    @XmlElement(name = "ScaleHandeling")
    protected ScaleHandeling scaleHandeling;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der sizeX-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSizeX() {
        return sizeX;
    }

    /**
     * Legt den Wert der sizeX-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSizeX(BigInteger value) {
        this.sizeX = value;
    }

    /**
     * Ruft den Wert der sizeY-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSizeY() {
        return sizeY;
    }

    /**
     * Legt den Wert der sizeY-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSizeY(BigInteger value) {
        this.sizeY = value;
    }

    /**
     * Ruft den Wert der source-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Source }
     *     
     */
    public Source getSource() {
        return source;
    }

    /**
     * Legt den Wert der source-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Source }
     *     
     */
    public void setSource(Source value) {
        this.source = value;
    }

    /**
     * Ruft den Wert der scaleHandeling-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ScaleHandeling }
     *     
     */
    public ScaleHandeling getScaleHandeling() {
        return scaleHandeling;
    }

    /**
     * Legt den Wert der scaleHandeling-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ScaleHandeling }
     *     
     */
    public void setScaleHandeling(ScaleHandeling value) {
        this.scaleHandeling = value;
    }

    /**
     * Ruft den Wert der uuid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }
    
    public UUID getUUID() {
    	return UUID.fromString(uuid);
    }

    /**
     * Legt den Wert der uuid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
