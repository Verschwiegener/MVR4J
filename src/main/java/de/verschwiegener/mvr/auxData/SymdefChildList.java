//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//

package de.verschwiegener.mvr.auxData;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import de.verschwiegener.mvr.nodes.Geometry3D;
import de.verschwiegener.mvr.nodes.Symbol;

/**
 * <p>
 * Java-Klasse für ChildList complex type.
 * 
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser
 * Klasse enthalten ist.
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
@XmlType(name = "SymdefChildList", propOrder = { "geometry3D", "symbol" })
public class SymdefChildList {

	@XmlElement(name = "Geometry3D")
	protected List<Geometry3D> geometry3D;
	
	@XmlElement(name = "Symbol")
	protected List<Symbol> symbol;

	/**
	 * Ruft den Wert der geometry3D-Eigenschaft ab.
	 * 
	 * @return possible object is {@link Geometry3D }
	 * 
	 */
	public List<Geometry3D> getGeometry3D() {
		if (geometry3D == null) {
			geometry3D = new ArrayList<Geometry3D>();
		}
		return geometry3D;
	}

	/**
	 * Ruft den Wert der symbol-Eigenschaft ab.
	 * 
	 * @return possible object is {@link Symbol }
	 * 
	 */
	public List<Symbol> getSymbol() {
		if (symbol == null) {
			symbol = new ArrayList<Symbol>();
		}
		return symbol;
	}

	
	public boolean hasChildren() {
		return getGeometry3D().isEmpty() || getSymbol().isEmpty();
	}

}
