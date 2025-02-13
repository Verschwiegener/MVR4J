//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 generiert 
// Siehe <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2024.05.02 um 12:46:28 AM CEST 
//


package de.verschwiegener.mvr.nodes;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für Network complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Network"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="geometry" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="ipv4" type="{}Ipv4_Adress" /&gt;
 *       &lt;attribute name="subnetmask" type="{}Ipv4_Adress" /&gt;
 *       &lt;attribute name="ipv6" type="{}Ipv6_Adress" /&gt;
 *       &lt;attribute name="dhcp" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="hostname" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Network")
public class Network {

    @XmlAttribute(name = "geometry", required = true)
    protected String geometry;
    @XmlAttribute(name = "ipv4")
    protected String ipv4;
    @XmlAttribute(name = "subnetmask")
    protected String subnetmask;
    @XmlAttribute(name = "ipv6")
    protected String ipv6;
    @XmlAttribute(name = "dhcp")
    protected String dhcp;
    @XmlAttribute(name = "hostname")
    protected String hostname;

    /**
     * Ruft den Wert der geometry-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Legt den Wert der geometry-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeometry(String value) {
        this.geometry = value;
    }

    /**
     * Ruft den Wert der ipv4-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InetAddress }
     * @throws UnknownHostException 
     *     
     */
    public InetAddress getIpv4() throws UnknownHostException {
    	if(ipv4 == null)
    		return null;
        return InetAddress.getByName(ipv4);
    }

    /**
     * Legt den Wert der ipv4-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InetAddress }
     *     
     */
    public void setIpv4(InetAddress value) {
        this.ipv4 = value.getHostName();
    }

    /**
     * Ruft den Wert der subnetmask-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InetAddress }
     * @throws UnknownHostException 
     *     
     */
    public InetAddress getSubnetmask() throws UnknownHostException {
    	if(subnetmask == null)
    		return null;
        return InetAddress.getByName(subnetmask);
    }

    /**
     * Legt den Wert der subnetmask-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InetAddress }
     *     
     */
    public void setSubnetmask(InetAddress value) {
        this.subnetmask = value.getHostName();
    }

    /**
     * Ruft den Wert der ipv6-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link InetAddress }
     * @throws UnknownHostException 
     *     
     */
    public InetAddress getIpv6() throws UnknownHostException {
    	if(ipv6 == null)
    		return null;
        return InetAddress.getByName(ipv6);
    }

    /**
     * Legt den Wert der ipv6-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link InetAddress }
     *     
     */
    public void setIpv6(InetAddress value) {
        this.ipv6 = value.getHostName();
    }

    /**
     * Ruft den Wert der dhcp-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDHCP() {
    	if(dhcp == null)
    		return false;
       return dhcp.equals("on");
    }

    /**
     * Legt den Wert der dhcp-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDHCP(boolean value) {
        this.dhcp = value ? "on" : "off";
    }

    /**
     * Ruft den Wert der hostname-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Legt den Wert der hostname-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }
    /**
     * Returns NetworkType of this Network, e.g. IPv4, IPv6, DHCP
     * @return
     */
    public NetworkType getNetworkType() {
    	if(ipv4 != null && hostname != null)
    		return NetworkType.IPv4;
    	if(ipv6 != null) 
    		return NetworkType.IPv6;
    	if(isDHCP())
    		return NetworkType.DHCP;
    	return NetworkType.NONE;
    }

	public enum NetworkType {

		IPv4, IPv6, DHCP, NONE;

	}

}
