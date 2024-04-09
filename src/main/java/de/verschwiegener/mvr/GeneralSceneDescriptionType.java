//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//


package de.verschwiegener.mvr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.auxData.SceneType;


/**
 * <p>Java-Klasse für GeneralSceneDescriptionType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GeneralSceneDescriptionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UserData" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Scene" type="{}SceneType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="verMajor" type="{http://www.w3.org/2001/XMLSchema}byte" /&gt;
 *       &lt;attribute name="verMinor" type="{http://www.w3.org/2001/XMLSchema}byte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeneralSceneDescriptionType", propOrder = {
    "userData",
    "scene"
})
public class GeneralSceneDescriptionType {

    @XmlElement(name = "UserData", required = true)
    protected String userData;
    @XmlElement(name = "Scene", required = true)
    protected SceneType scene;
    @XmlAttribute(name = "verMajor")
    protected Byte verMajor;
    @XmlAttribute(name = "verMinor")
    protected Byte verMinor;

    /**
     * Ruft den Wert der userData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserData() {
        return userData;
    }

    /**
     * Legt den Wert der userData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserData(String value) {
        this.userData = value;
    }

    /**
     * Ruft den Wert der scene-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link SceneType }
     *     
     */
    public SceneType getScene() {
        return scene;
    }

    /**
     * Legt den Wert der scene-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link SceneType }
     *     
     */
    public void setScene(SceneType value) {
        this.scene = value;
    }

    /**
     * Ruft den Wert der verMajor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getVerMajor() {
        return verMajor;
    }

    /**
     * Legt den Wert der verMajor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setVerMajor(Byte value) {
        this.verMajor = value;
    }

    /**
     * Ruft den Wert der verMinor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getVerMinor() {
        return verMinor;
    }

    /**
     * Legt den Wert der verMinor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setVerMinor(Byte value) {
        this.verMinor = value;
    }

}
