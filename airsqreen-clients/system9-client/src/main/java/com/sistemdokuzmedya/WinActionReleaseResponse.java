
package com.sistemdokuzmedya;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Win_Action_ReleaseResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "winActionReleaseResult"
})
@XmlRootElement(name = "Win_Action_ReleaseResponse")
public class WinActionReleaseResponse {

    @XmlElement(name = "Win_Action_ReleaseResult")
    protected String winActionReleaseResult;

    /**
     * Gets the value of the winActionReleaseResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWinActionReleaseResult() {
        return winActionReleaseResult;
    }

    /**
     * Sets the value of the winActionReleaseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWinActionReleaseResult(String value) {
        this.winActionReleaseResult = value;
    }

}
