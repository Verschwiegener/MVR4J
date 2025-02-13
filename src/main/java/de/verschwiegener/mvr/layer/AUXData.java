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

import de.verschwiegener.mvr.auxData.Symdef;
import de.verschwiegener.mvr.nodes.MappingDefinition;
import de.verschwiegener.mvr.nodes.Position;


/**
 * <p>Java-Klasse für AUXData complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="AUXData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Class" type="{}Class" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Symdef" type="{}Symdef" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Position" type="{}Position" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="MappingDefinition" type="{}MappingDefinition" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AUXData", propOrder = {
    "clazz",
    "symdef",
    "position",
    "mappingDefinition"
})
public class AUXData {

    @XmlElement(name = "Class")
    protected List<Classing> clazz;
    @XmlElement(name = "Symdef")
    protected List<Symdef> symdef;
    @XmlElement(name = "Position")
    protected List<Position> position;
    @XmlElement(name = "MappingDefinition")
    protected List<MappingDefinition> mappingDefinition;

    /**
     * Gets the value of the clazz property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clazz property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClazz().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Classing }
     * 
     * 
     */
    public List<Classing> getClazz() {
        if (clazz == null) {
            clazz = new ArrayList<Classing>();
        }
        return this.clazz;
    }

    /**
     * Gets the value of the symdef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the symdef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSymdef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Symdef }
     * 
     * 
     */
    public List<Symdef> getSymdef() {
        if (symdef == null) {
            symdef = new ArrayList<Symdef>();
        }
        return this.symdef;
    }

    /**
     * Gets the value of the position property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the position property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Position }
     * 
     * 
     */
    public List<Position> getPosition() {
        if (position == null) {
            position = new ArrayList<Position>();
        }
        return this.position;
    }

    /**
     * Gets the value of the mappingDefinition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mappingDefinition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMappingDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MappingDefinition }
     * 
     * 
     */
    public List<MappingDefinition> getMappingDefinition() {
        if (mappingDefinition == null) {
            mappingDefinition = new ArrayList<MappingDefinition>();
        }
        return this.mappingDefinition;
    }

}
