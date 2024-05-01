//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.nodes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für scaleenum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="scaleenum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ScaleKeepRatio"/&gt;
 *     &lt;enumeration value="ScaleIgnoreRatio"/&gt;
 *     &lt;enumeration value="KeepSizeCenter"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "scaleenum")
@XmlEnum
public enum Scaleenum {

    @XmlEnumValue("ScaleKeepRatio")
    SCALE_KEEP_RATIO("ScaleKeepRatio"),
    @XmlEnumValue("ScaleIgnoreRatio")
    SCALE_IGNORE_RATIO("ScaleIgnoreRatio"),
    @XmlEnumValue("KeepSizeCenter")
    KEEP_SIZE_CENTER("KeepSizeCenter");
    private final String value;

    Scaleenum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Scaleenum fromValue(String v) {
        for (Scaleenum c: Scaleenum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
