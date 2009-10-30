//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.10.30 at 05:30:06 PM WET 
//


package pt.com.broker.monitorization.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agents" type="{}Agents"/>
 *         &lt;element name="tcp-port-exceptions" type="{}ExceptionAgents" minOccurs="0"/>
 *         &lt;element name="global-config-file">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="location" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "agents",
    "tcpPortExceptions",
    "globalConfigFile"
})
@XmlRootElement(name = "monitorization-configuration")
public class MonitorizationConfiguration {

    @XmlElement(required = true)
    protected Agents agents;
    @XmlElement(name = "tcp-port-exceptions")
    protected ExceptionAgents tcpPortExceptions;
    @XmlElement(name = "global-config-file", required = true)
    protected MonitorizationConfiguration.GlobalConfigFile globalConfigFile;

    /**
     * Gets the value of the agents property.
     * 
     * @return
     *     possible object is
     *     {@link Agents }
     *     
     */
    public Agents getAgents() {
        return agents;
    }

    /**
     * Sets the value of the agents property.
     * 
     * @param value
     *     allowed object is
     *     {@link Agents }
     *     
     */
    public void setAgents(Agents value) {
        this.agents = value;
    }

    /**
     * Gets the value of the tcpPortExceptions property.
     * 
     * @return
     *     possible object is
     *     {@link ExceptionAgents }
     *     
     */
    public ExceptionAgents getTcpPortExceptions() {
        return tcpPortExceptions;
    }

    /**
     * Sets the value of the tcpPortExceptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExceptionAgents }
     *     
     */
    public void setTcpPortExceptions(ExceptionAgents value) {
        this.tcpPortExceptions = value;
    }

    /**
     * Gets the value of the globalConfigFile property.
     * 
     * @return
     *     possible object is
     *     {@link MonitorizationConfiguration.GlobalConfigFile }
     *     
     */
    public MonitorizationConfiguration.GlobalConfigFile getGlobalConfigFile() {
        return globalConfigFile;
    }

    /**
     * Sets the value of the globalConfigFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonitorizationConfiguration.GlobalConfigFile }
     *     
     */
    public void setGlobalConfigFile(MonitorizationConfiguration.GlobalConfigFile value) {
        this.globalConfigFile = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="location" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class GlobalConfigFile {

        @XmlAttribute(required = true)
        protected String location;

        /**
         * Gets the value of the location property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLocation() {
            return location;
        }

        /**
         * Sets the value of the location property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLocation(String value) {
            this.location = value;
        }

    }

}
