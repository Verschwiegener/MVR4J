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
 * <p>Java-Klasse für SourceEnum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="SourceEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="NDI"/&gt;
 *     &lt;enumeration value="File"/&gt;
 *     &lt;enumeration value="CITP"/&gt;
 *     &lt;enumeration value="CaptureDevice"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SourceEnum")
@XmlEnum
public enum SourceEnum {

    NDI("NDI"),
    @XmlEnumValue("File")
    FILE("File"),
    CITP("CITP"),
    @XmlEnumValue("CaptureDevice")
    CAPTURE_DEVICE("CaptureDevice");
    private final String value;

    SourceEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SourceEnum fromValue(String v) {
        for (SourceEnum c: SourceEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
