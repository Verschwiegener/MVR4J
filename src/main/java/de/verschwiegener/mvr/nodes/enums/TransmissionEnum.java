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
 * <p>Java-Klasse für Transmission_enum.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="Transmission_enum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Unicast"/&gt;
 *     &lt;enumeration value="Multicast"/&gt;
 *     &lt;enumeration value="Broadcast"/&gt;
 *     &lt;enumeration value="Anycast"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Transmission_enum")
@XmlEnum
public enum TransmissionEnum {

    @XmlEnumValue("Unicast")
    UNICAST("Unicast"),
    @XmlEnumValue("Multicast")
    MULTICAST("Multicast"),
    @XmlEnumValue("Broadcast")
    BROADCAST("Broadcast"),
    @XmlEnumValue("Anycast")
    ANYCAST("Anycast");
    private final String value;

    TransmissionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransmissionEnum fromValue(String v) {
        for (TransmissionEnum c: TransmissionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
