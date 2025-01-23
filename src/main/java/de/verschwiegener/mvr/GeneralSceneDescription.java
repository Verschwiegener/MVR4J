//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.nodes.UserData;

/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserData" type="{}UserData" minOccurs="0"/&gt;
 *         &lt;element name="Scene" type="{}Scene"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="verMajor" use="required" type="{}positiveinteger" /&gt;
 *       &lt;attribute name="verMinor" use="required" type="{}positiveinteger" /&gt;
 *       &lt;attribute name="provider" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="providerVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userData",
    "scene"
})
@XmlRootElement(name = "GeneralSceneDescription")
public class GeneralSceneDescription {

    @XmlElement(name = "UserData")
    protected UserData userData;
    @XmlElement(name = "Scene", required = true)
    protected Scene scene;
    @XmlAttribute(name = "verMajor", required = true)
    protected int verMajor;
    @XmlAttribute(name = "verMinor", required = true)
    protected int verMinor;
    @XmlAttribute(name = "provider", required = true)
    protected String provider;
    @XmlAttribute(name = "providerVersion", required = true)
    protected String providerVersion;
    

    public GeneralSceneDescription() {
	}
    
    /**
     * Ruft den Wert der userData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link UserData }
     *     
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * Legt den Wert der userData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link UserData }
     *     
     */
    public void setUserData(UserData value) {
        this.userData = value;
    }

    /**
     * Ruft den Wert der scene-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Scene }
     *     
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Legt den Wert der scene-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Scene }
     *     
     */
    public void setScene(Scene value) {
        this.scene = value;
    }

    /**
     * Ruft den Wert der verMajor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getVerMajor() {
        return verMajor;
    }

    /**
     * Legt den Wert der verMajor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVerMajor(int value) {
        this.verMajor = value;
    }

    /**
     * Ruft den Wert der verMinor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getVerMinor() {
        return verMinor;
    }

    /**
     * Legt den Wert der verMinor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVerMinor(int value) {
        this.verMinor = value;
    }

    /**
     * Ruft den Wert der provider-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Legt den Wert der provider-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvider(String value) {
        this.provider = value;
    }

    /**
     * Ruft den Wert der providerVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderVersion() {
        return providerVersion;
    }

    /**
     * Legt den Wert der providerVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderVersion(String value) {
        this.providerVersion = value;
    }

}
