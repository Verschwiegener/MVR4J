//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.layer;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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


/**
 * <p>Java-Klasse für ChildList complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ChildList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SceneObject" type="{}SceneObject" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="GroupObject" type="{}GroupObject" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="FocusPoint" type="{}FocusPoint" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Fixture" type="{}Fixture" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Support" type="{}Support" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Truss" type="{}Truss" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="VideoScreen" type="{}VideoScreen" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Projector" type="{}Projector" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildList", propOrder = {
    "sceneObject",
    "groupObject",
    "focusPoint",
    "fixture",
    "support",
    "truss",
    "videoScreen",
    "projector"
})
public class ChildList {

    @XmlElement(name = "SceneObject")
    protected List<SceneObject> sceneObject;
    @XmlElement(name = "GroupObject")
    protected List<GroupObject> groupObject;
    @XmlElement(name = "FocusPoint")
    protected List<FocusPoint> focusPoint;
    @XmlElement(name = "Fixture")
    protected List<Fixture> fixture;
    @XmlElement(name = "Support")
    protected List<Support> support;
    @XmlElement(name = "Truss")
    protected List<Truss> truss;
    @XmlElement(name = "VideoScreen")
    protected List<VideoScreen> videoScreen;
    @XmlElement(name = "Projector")
    protected List<Projector> projector;

    /**
     * Gets the value of the sceneObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sceneObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSceneObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SceneObject }
     * 
     * 
     */
    public List<SceneObject> getSceneObject() {
        if (sceneObject == null) {
            sceneObject = new ArrayList<SceneObject>();
        }
        return this.sceneObject;
    }

    /**
     * Gets the value of the groupObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroupObject }
     * 
     * 
     */
    public List<GroupObject> getGroupObject() {
        if (groupObject == null) {
            groupObject = new ArrayList<GroupObject>();
        }
        return this.groupObject;
    }

    /**
     * Gets the value of the focusPoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the focusPoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFocusPoint().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FocusPoint }
     * 
     * 
     */
    public List<FocusPoint> getFocusPoint() {
        if (focusPoint == null) {
            focusPoint = new ArrayList<FocusPoint>();
        }
        return this.focusPoint;
    }

    /**
     * Gets the value of the fixture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fixture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFixture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fixture }
     * 
     * 
     */
    public List<Fixture> getFixture() {
        if (fixture == null) {
            fixture = new ArrayList<Fixture>();
        }
        return this.fixture;
    }

    /**
     * Gets the value of the support property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the support property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Support }
     * 
     * 
     */
    public List<Support> getSupport() {
        if (support == null) {
            support = new ArrayList<Support>();
        }
        return this.support;
    }

    /**
     * Gets the value of the truss property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the truss property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTruss().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Truss }
     * 
     * 
     */
    public List<Truss> getTruss() {
        if (truss == null) {
            truss = new ArrayList<Truss>();
        }
        return this.truss;
    }

    /**
     * Gets the value of the videoScreen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the videoScreen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVideoScreen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VideoScreen }
     * 
     * 
     */
    public List<VideoScreen> getVideoScreen() {
        if (videoScreen == null) {
            videoScreen = new ArrayList<VideoScreen>();
        }
        return this.videoScreen;
    }

    /**
     * Gets the value of the projector property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projector property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjector().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Projector }
     * 
     * 
     */
    public List<Projector> getProjector() {
        if (projector == null) {
            projector = new ArrayList<Projector>();
        }
        return this.projector;
    }
    
    public boolean hasChildren() {
    	return !getSceneObject().isEmpty() || !getGroupObject().isEmpty() || !getFocusPoint().isEmpty() || !getFixture().isEmpty() || !getSupport().isEmpty() || !getVideoScreen().isEmpty() || !getProjector().isEmpty();
    }

}
