//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//

package de.verschwiegener.mvr.auxData;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.nodes.Geometry3D;
import de.verschwiegener.mvr.nodes.Symbol;

/**
 * <p>
 * Java-Klasse für Symdef complex type.
 * 
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser
 * Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Symdef"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Geometry3D" type="{}Geometry3D"/&gt;
 *         &lt;element name="Symbol" type="{}Symbol"/&gt;
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
@XmlType(name = "Symdef", propOrder = { "childList" })
public class Symdef {

	@XmlElement(name = "ChildList", required = false)
	protected SymdefChildList childList;

	@XmlAttribute(name = "uuid", required = true)
	protected String uuid;
	@XmlAttribute(name = "name")
	protected String name;

	/**
	 * Ruft den Wert der childList-Eigenschaft ab.
	 * 
	 * @return possible object is {@link SymdefChildList }
	 * 
	 */
	public SymdefChildList getChildList() {
		return childList;
	}
	
	public boolean hasChildren() {
    	return getChildList() != null && getChildList().hasChildren();
    }

	/**
	 * Legt den Wert der childList-Eigenschaft fest.
	 * 
	 * @param value allowed object is {@link SymdefChildList }
	 * 
	 */
	public void setChildList(SymdefChildList childList) {
		this.childList = childList;
	}

	/**
	 * Ruft den Wert der uuid-Eigenschaft ab.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUuid() {
		return uuid;
	}
	
	public UUID uuid() {
		return UUID.fromString(uuid);
	}

	/**
	 * Legt den Wert der uuid-Eigenschaft fest.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUuid(String value) {
		this.uuid = value;
	}

	/**
	 * Ruft den Wert der name-Eigenschaft ab.
	 * 
	 * @return possible object is {@link String }
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
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}
	
	public Geometry3D getGeometry() {
		if(getChildList() == null) return null;
		return getChildList().getGeometry3D();
	}
	
	public Symbol getSymbol() {
		if(getChildList() == null) return null;
		return getChildList().getSymbol();
	}

}
