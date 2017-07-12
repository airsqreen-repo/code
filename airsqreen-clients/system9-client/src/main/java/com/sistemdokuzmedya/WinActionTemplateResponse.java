
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
 *         &lt;element name="Win_Action_TemplateResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "winActionTemplateResult"
})
@XmlRootElement(name = "Win_Action_TemplateResponse")
public class WinActionTemplateResponse {

    @XmlElement(name = "Win_Action_TemplateResult")
    protected String winActionTemplateResult;

    /**
     * Gets the value of the winActionTemplateResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWinActionTemplateResult() {
        return winActionTemplateResult;
    }

    /**
     * Sets the value of the winActionTemplateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWinActionTemplateResult(String value) {
        this.winActionTemplateResult = value;
    }

}
