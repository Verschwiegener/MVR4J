//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.layer.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.layer.ChildList;
import de.verschwiegener.mvr.util.MVRMatrix;


/**
 * <p>Java-Klasse für GroupObject complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GroupObject"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Matrix" type="{}matrixtype" minOccurs="0"/&gt;
 *         &lt;element name="Classing" type="{}guidtype" minOccurs="0"/&gt;
 *         &lt;element name="ChildList" type="{}ChildList"/&gt;
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
@XmlType(name = "GroupObject", propOrder = {
    "matrix",
    "classing",
    "childList"
})
public class GroupObject {

    @XmlElement(name = "Matrix")
    protected String matrix;
    @XmlElement(name = "Classing")
    protected String classing;
    @XmlElement(name = "ChildList", required = true)
    protected ChildList childList;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Ruft den Wert der matrix-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatrix() {
        return matrix;
    }
    
    public MVRMatrix matrix() {
    	return new MVRMatrix(matrix);
    }

    /**
     * Legt den Wert der matrix-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatrix(String value) {
        this.matrix = value;
    }

    /**
     * Ruft den Wert der classing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassing() {
        return classing;
    }

    /**
     * Legt den Wert der classing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassing(String value) {
        this.classing = value;
    }

    /**
     * Ruft den Wert der childList-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ChildList }
     *     
     */
    public ChildList getChildList() {
        return childList;
    }

    /**
     * Legt den Wert der childList-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildList }
     *     
     */
    public void setChildList(ChildList value) {
        this.childList = value;
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
