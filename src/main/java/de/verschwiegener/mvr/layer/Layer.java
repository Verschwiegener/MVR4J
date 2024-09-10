//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.layer;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.layer.type.Fixture;
import de.verschwiegener.mvr.layer.type.FocusPoint;
import de.verschwiegener.mvr.layer.type.GroupObject;
import de.verschwiegener.mvr.layer.type.Projector;
import de.verschwiegener.mvr.layer.type.SceneObject;
import de.verschwiegener.mvr.layer.type.Support;
import de.verschwiegener.mvr.layer.type.Truss;
import de.verschwiegener.mvr.layer.type.VideoScreen;
import de.verschwiegener.mvr.nodes.Projections;
import de.verschwiegener.mvr.util.MVRMatrix;


/**
 * <p>Java-Klasse für Layer complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Layer"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Matrix" type="{}matrixtype" minOccurs="0"/&gt;
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
@XmlType(name = "Layer", propOrder = {
    "matrix",
    "childList"
})
public class Layer {

    @XmlElement(name = "Matrix")
    protected String matrix;
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
    
    public boolean hasChildren() {
    	return getChildList() != null && getChildList().hasChildren();
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
     * Returns UUID
     * @return
     */
    public UUID uuid() {
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
    
    public List<SceneObject> getSceneObjects(){
    	return getChildList().getSceneObject();
    }
    
    public SceneObject getSceneObjectByUUID(UUID uuid) {
    	return getSceneObjects().stream().filter(so -> so.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<GroupObject> getGroupObjects() {
    	return getChildList().getGroupObject();
    }
    
    public GroupObject getGroupObjectByUUID(UUID uuid) {
    	return getGroupObjects().stream().filter(go -> go.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<FocusPoint> getFocusPoints() {
		return getChildList().getFocusPoint();
	}
    
    /**
     * Returns FocusPoint with correct UUID or null
     * @param uuid
     * @return
     */
    public FocusPoint getFocusPointByUUID(UUID uuid) {
    	return getFocusPoints().stream().filter(fp -> fp.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<Fixture> getFixtures() {
		return getChildList().getFixture();
	}
    
    /**
     * Returns Fixture with correct UUID or null
     * @param uuid
     * @return
     */
    public Fixture getFixtureByUUID(UUID uuid) {
    	return getFixtures().stream().filter(f -> f.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<Support> getSupports() {
    	return getChildList().getSupport();
    }
    
    public Support getSupportByUUID(UUID uuid) {
    	return getSupports().stream().filter(s -> s.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<Truss> getTrusses() {
    	return getChildList().getTruss();
    }
    
    public Truss getTrussByUUID(UUID uuid) {
    	return getTrusses().stream().filter(t -> t.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
    
    public List<VideoScreen> getVideoScreens() {
    	return getChildList().getVideoScreen();
    }
    
    public VideoScreen getVideoScreenByUUID(UUID uuid) {
    	return getVideoScreens().stream().filter(vs -> vs.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }

    public List<Projector> getProjectors() {
    	return getChildList().getProjector();
    }
    
    public Projector getProjectorByUUID(UUID uuid) {
    	return getProjectors().stream().filter(p -> p.getUUID().compareTo(uuid) == 0).findFirst().orElse(null);
    }
}
