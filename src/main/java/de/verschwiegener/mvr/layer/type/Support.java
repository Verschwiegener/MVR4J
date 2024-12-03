//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.layer.type;

import java.math.BigInteger;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.joml.Matrix4f;

import de.verschwiegener.mvr.auxData.Addresses;
import de.verschwiegener.mvr.layer.ChildList;
import de.verschwiegener.mvr.nodes.Alignments;
import de.verschwiegener.mvr.nodes.Connections;
import de.verschwiegener.mvr.nodes.CustomCommands;
import de.verschwiegener.mvr.nodes.Geometries;
import de.verschwiegener.mvr.nodes.Overwrites;
import de.verschwiegener.mvr.util.MVRUtils;


/**
 * <p>Java-Klasse für Support complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Support"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Matrix" type="{}matrixtype" minOccurs="0"/&gt;
 *         &lt;element name="Classing" type="{}guidtype" minOccurs="0"/&gt;
 *         &lt;element name="Position" type="{}guidtype" minOccurs="0"/&gt;
 *         &lt;element name="Geometries" type="{}Geometries"/&gt;
 *         &lt;element name="Function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ChainLength" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="GDTFSpec" type="{}FileName" minOccurs="0"/&gt;
 *         &lt;element name="GDTFMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Addresses" type="{}Addresses" minOccurs="0"/&gt;
 *         &lt;element name="Alignments" type="{}Alignments" minOccurs="0"/&gt;
 *         &lt;element name="CustomCommands" type="{}CustomCommands" minOccurs="0"/&gt;
 *         &lt;element name="Overwrites" type="{}Overwrites" minOccurs="0"/&gt;
 *         &lt;element name="Connections" type="{}Connections" minOccurs="0"/&gt;
 *         &lt;element name="FixtureID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FixtureIDNumeric" type="{}positiveinteger"/&gt;
 *         &lt;element name="UnitNumber" type="{}positiveinteger" minOccurs="0"/&gt;
 *         &lt;element name="CustomIdType" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="CustomId" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="ChildList" type="{}ChildList"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="uuid" use="required" type="{}guidtype" /&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="" /&gt;
 *       &lt;attribute name="multipatch" type="{}guidtype" default="" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Support", propOrder = {
    "matrix",
    "classing",
    "position",
    "geometries",
    "function",
    "chainLength",
    "gdtfSpec",
    "gdtfMode",
    "addresses",
    "alignments",
    "customCommands",
    "overwrites",
    "connections",
    "fixtureID",
    "fixtureIDNumeric",
    "unitNumber",
    "customIdType",
    "customId",
    "childList"
})
public class Support {

    @XmlElement(name = "Matrix")
    protected String matrix;
    @XmlElement(name = "Classing")
    protected String classing;
    @XmlElement(name = "Position")
    protected String position;
    @XmlElement(name = "Geometries", required = true)
    protected Geometries geometries;
    @XmlElement(name = "Function")
    protected String function;
    @XmlElement(name = "ChainLength")
    protected float chainLength;
    @XmlElement(name = "GDTFSpec")
    protected String gdtfSpec;
    @XmlElement(name = "GDTFMode")
    protected String gdtfMode;
    @XmlElement(name = "Addresses")
    protected Addresses addresses;
    @XmlElement(name = "Alignments")
    protected Alignments alignments;
    @XmlElement(name = "CustomCommands")
    protected CustomCommands customCommands;
    @XmlElement(name = "Overwrites")
    protected Overwrites overwrites;
    @XmlElement(name = "Connections")
    protected Connections connections;
    @XmlElement(name = "FixtureID", required = true)
    protected String fixtureID;
    @XmlElement(name = "FixtureIDNumeric", required = true)
    protected BigInteger fixtureIDNumeric;
    @XmlElement(name = "UnitNumber")
    protected BigInteger unitNumber;
    @XmlElement(name = "CustomIdType")
    protected BigInteger customIdType;
    @XmlElement(name = "CustomId")
    protected BigInteger customId;
    @XmlElement(name = "ChildList", required = true)
    protected ChildList childList;
    @XmlAttribute(name = "uuid", required = true)
    protected String uuid;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "multipatch")
    protected String multipatch;

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
     * Ruft den Wert der position-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Legt den Wert der position-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
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
     * Ruft den Wert der function-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Legt den Wert der function-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Ruft den Wert der chainLength-Eigenschaft ab.
     * 
     */
    public float getChainLength() {
        return chainLength;
    }

    /**
     * Legt den Wert der chainLength-Eigenschaft fest.
     * 
     */
    public void setChainLength(float value) {
        this.chainLength = value;
    }

    /**
     * Ruft den Wert der gdtfSpec-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGDTFSpec() {
        return gdtfSpec;
    }

    /**
     * Legt den Wert der gdtfSpec-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGDTFSpec(String value) {
        this.gdtfSpec = value;
    }

    /**
     * Ruft den Wert der gdtfMode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGDTFMode() {
        return gdtfMode;
    }

    /**
     * Legt den Wert der gdtfMode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGDTFMode(String value) {
        this.gdtfMode = value;
    }

    /**
     * Ruft den Wert der addresses-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Addresses }
     *     
     */
    public Addresses getAddresses() {
        return addresses;
    }

    /**
     * Legt den Wert der addresses-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Addresses }
     *     
     */
    public void setAddresses(Addresses value) {
        this.addresses = value;
    }

    /**
     * Ruft den Wert der alignments-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Alignments }
     *     
     */
    public Alignments getAlignments() {
        return alignments;
    }

    /**
     * Legt den Wert der alignments-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Alignments }
     *     
     */
    public void setAlignments(Alignments value) {
        this.alignments = value;
    }

    /**
     * Ruft den Wert der customCommands-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link CustomCommands }
     *     
     */
    public CustomCommands getCustomCommands() {
        return customCommands;
    }

    /**
     * Legt den Wert der customCommands-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomCommands }
     *     
     */
    public void setCustomCommands(CustomCommands value) {
        this.customCommands = value;
    }

    /**
     * Ruft den Wert der overwrites-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Overwrites }
     *     
     */
    public Overwrites getOverwrites() {
        return overwrites;
    }

    /**
     * Legt den Wert der overwrites-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Overwrites }
     *     
     */
    public void setOverwrites(Overwrites value) {
        this.overwrites = value;
    }

    /**
     * Ruft den Wert der connections-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Connections }
     *     
     */
    public Connections getConnections() {
        return connections;
    }

    /**
     * Legt den Wert der connections-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Connections }
     *     
     */
    public void setConnections(Connections value) {
        this.connections = value;
    }

    /**
     * Ruft den Wert der fixtureID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFixtureID() {
        return fixtureID;
    }

    /**
     * Legt den Wert der fixtureID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFixtureID(String value) {
        this.fixtureID = value;
    }

    /**
     * Ruft den Wert der fixtureIDNumeric-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFixtureIDNumeric() {
        return fixtureIDNumeric;
    }

    /**
     * Legt den Wert der fixtureIDNumeric-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFixtureIDNumeric(BigInteger value) {
        this.fixtureIDNumeric = value;
    }

    /**
     * Ruft den Wert der unitNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUnitNumber() {
        return unitNumber;
    }

    /**
     * Legt den Wert der unitNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUnitNumber(BigInteger value) {
        this.unitNumber = value;
    }

    /**
     * Ruft den Wert der customIdType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCustomIdType() {
        return customIdType;
    }

    /**
     * Legt den Wert der customIdType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomIdType(BigInteger value) {
        this.customIdType = value;
    }

    /**
     * Ruft den Wert der customId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCustomId() {
        return customId;
    }

    /**
     * Legt den Wert der customId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomId(BigInteger value) {
        this.customId = value;
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

    /**
     * Ruft den Wert der multipatch-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link UUID }
     *     
     */
    public UUID getMultipatch() {
        return UUID.fromString(multipatch);
    }

    /**
     * Legt den Wert der multipatch-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link UUID }
     *     
     */
    public void setMultipatch(UUID value) {
        this.multipatch = value.toString();
    }

}
