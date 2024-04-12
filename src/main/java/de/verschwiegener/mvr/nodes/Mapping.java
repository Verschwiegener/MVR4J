//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//

package de.verschwiegener.mvr.nodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.layer.LayerType;

/**
 * <p>
 * Java-Klasse für LayerType complex type.
 * 
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser
 * Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="LayerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ChildList" type="{}ChildListType"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alignment", propOrder = { "content" })
public class Mapping {

	@XmlElementRefs({ @XmlElementRef(name = "ux", type = JAXBElement.class, required = false),
			@XmlElementRef(name = "uy", type = JAXBElement.class, required = false),
			@XmlElementRef(name = "ox", type = JAXBElement.class, required = false),
			@XmlElementRef(name = "oy", type = JAXBElement.class, required = false),
			@XmlElementRef(name = "rz", type = JAXBElement.class, required = false) })
	@XmlMixed
	protected List<Serializable> content;

	/**
	 * The unique identifier of the MappingDefinition node that will be the source
	 * of the mapping.
	 */
	@XmlAttribute(name = "linkDef")
	protected String linkDef;

	public List<Serializable> getContent() {
		if (content == null) {
			content = new ArrayList<Serializable>();
		}
		return this.content;
	}

	public String getLinkDef() {
		return linkDef;
	}

	public void setLinkDef(String linkDef) {
		this.linkDef = linkDef;
	}

}
