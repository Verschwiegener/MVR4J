//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//

package de.verschwiegener.mvr.nodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java-Klasse für GeometriesType complex type.
 * 
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser
 * Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GeometriesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Symbol" type="{}SymbolType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeometriesType", propOrder = { "symbol", "geometry3D" })
public class GeometriesType {

	@XmlElement(name = "Symbol", required = true)
	protected SymbolType symbol;

	@XmlElement(name = "Geometry3D", required = true)
	protected Geometry3DType geometry3D;

	/**
	 * Ruft den Wert der symbol-Eigenschaft ab.
	 * 
	 * @return possible object is {@link SymbolType }
	 * 
	 */
	public SymbolType getSymbol() {
		return symbol;
	}

	/**
	 * Legt den Wert der symbol-Eigenschaft fest.
	 * 
	 * @param value allowed object is {@link SymbolType }
	 * 
	 */
	public void setSymbol(SymbolType value) {
		this.symbol = value;
	}

	public Geometry3DType getGeometry3D() {
		return geometry3D;
	}

	public void setGeometry3D(Geometry3DType geometry3d) {
		geometry3D = geometry3d;
	}

}
