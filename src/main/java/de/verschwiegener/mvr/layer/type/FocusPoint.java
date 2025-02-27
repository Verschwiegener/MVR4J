//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.layer.type;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.joml.Matrix4f;

import de.verschwiegener.mvr.nodes.Geometries;
import de.verschwiegener.mvr.util.MVRUtils;


/**
 * <p>Java-Klasse für FocusPoint complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="FocusPoint"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Matrix" type="{}matrixtype" minOccurs="0"/&gt;
 *         &lt;element name="Classing" type="{}guidtype" minOccurs="0"/&gt;
 *         &lt;element name="Geometries" type="{}Geometries"/&gt;
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
@XmlType(name = "FocusPoint", propOrder = {
    "matrix",
    "classing",
    "geometries"
})
public class FocusPoint {

    @XmlElement(name = "Matrix")
    protected String matrix;
    @XmlElement(name = "Classing")
    protected String classing;
    @XmlElement(name = "Geometries", required = true)
    protected Geometries geometries;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     *  Considered identity when missing
     *  
     * @return
     */
    public Matrix4f matrix() {
    	return MVRUtils.toMatrix(matrix);
    }

    /**
     * Legt den Wert der matrix-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatrix(Matrix4f value) {
        this.matrix = MVRUtils.toGDTFMatrix(value);
    }

    /**
     * Ruft den Wert der classing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link UUID }
     *     
     */
    public UUID getClassing() {
        return UUID.fromString(classing);
    }

    /**
     * Legt den Wert der classing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link UUID }
     *     
     */
    public void setClassing(UUID value) {
        this.classing = value.toString();
    }

    /**
     * Ruft den Wert der geometries-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Geometries }
     *     
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * Legt den Wert der geometries-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Geometries }
     *     
     */
    public void setGeometries(Geometries value) {
        this.geometries = value;
    }

    /**
     * Ruft den Wert der uuid-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link UUID }
     *     
     */
    public UUID getUUID() {
    	return UUID.fromString(uuid);
    }

    /**
     * Legt den Wert der uuid-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link UUID }
     *     
     */
    public void setUUID(UUID value) {
        this.uuid = value.toString();
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
