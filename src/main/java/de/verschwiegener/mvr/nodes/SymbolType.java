//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.03.26 um 11:42:03 PM CET 
//

package de.verschwiegener.mvr.nodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 * <p>
 * Java-Klasse für SymbolType complex type.
 * 
 * <p>
 * Das folgende Schemafragment gibt den erwarteten Content an, der in dieser
 * Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SymbolType"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *       &lt;attribute name="symdef" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="uuid" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SymbolType", propOrder = { "matrix" })
public class SymbolType {

	@XmlAttribute(name = "symdef")
	protected String symdef;
	@XmlAttribute(name = "uuid")
	protected String uuid;

	@XmlElement(name = "Matrix")
	protected Matrix matrix;

	/**
	 * Ruft den Wert der symdef-Eigenschaft ab.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSymdef() {
		return symdef;
	}

	/**
	 * Legt den Wert der symdef-Eigenschaft fest.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSymdef(String value) {
		this.symdef = value;
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

	/**
	 * Legt den Wert der uuid-Eigenschaft fest.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUuid(String value) {
		this.uuid = value;
	}

	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

}