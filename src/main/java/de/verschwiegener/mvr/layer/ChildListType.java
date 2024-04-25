//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//

package de.verschwiegener.mvr.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

import static de.verschwiegener.mvr.MVRUtil.*;
import de.verschwiegener.mvr.layer.type.Fixture;
import de.verschwiegener.mvr.layer.type.FocusPoint;
import de.verschwiegener.mvr.layer.type.GroupObjectType;
import de.verschwiegener.mvr.layer.type.SceneObject;
import de.verschwiegener.mvr.nodes.Geometry3DType;


/**
 * <p>Java-Klasse für ChildListType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ChildListType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="SceneObject" type="{}SceneObjectType"/&gt;
 *         &lt;element name="Fixture" type="{}FixtureType"/&gt;
 *         &lt;element name="Geometry3D" type="{}Geometry3DType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChildListType", propOrder = {
    "content"
})
public class ChildListType {

    @XmlElementRefs({
        @XmlElementRef(name = "SceneObject", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Fixture", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Geometry3D", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link SceneObject }{@code >}
     * {@link JAXBElement }{@code <}{@link Fixture }{@code >}
     * {@link JAXBElement }{@code <}{@link Geometry3DType }{@code >}
     * {@link String }
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }
    
    public List<SceneObject> getSceneObjects() {
    	return getObject(MVR_SCENEOBJECT, getContent());
    }
    public List<Fixture> getFixtures() {
    	return getObject(MVR_FIXTURE, getContent());
    }
    public List<GroupObjectType> getGroupObject() {
    	return getObject(MVR_GROUPOBJECT, getContent());
    }
    public List<FocusPoint> getFocusPoint() {
    	return getObject(MVR_FOCUSPOINT, getContent());
    }
    
    /**
     * Returns Geometry3D Type, only for AUXData
     * @return
     */
    public List<Geometry3DType> getGeometry3D() {
    	return getObject(MVR_GEOMETRY3D, getContent());
    }

}
