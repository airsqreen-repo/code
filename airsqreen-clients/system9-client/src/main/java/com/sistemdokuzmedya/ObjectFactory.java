
package com.sistemdokuzmedya;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sistemdokuzmedya package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sistemdokuzmedya
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WinActionTemplate }
     * 
     */
    public WinActionTemplate createWinActionTemplate() {
        return new WinActionTemplate();
    }

    /**
     * Create an instance of {@link WinActionTemplateResponse }
     * 
     */
    public WinActionTemplateResponse createWinActionTemplateResponse() {
        return new WinActionTemplateResponse();
    }

    /**
     * Create an instance of {@link WinActionRelease }
     * 
     */
    public WinActionRelease createWinActionRelease() {
        return new WinActionRelease();
    }

    /**
     * Create an instance of {@link WinActionReleaseResponse }
     * 
     */
    public WinActionReleaseResponse createWinActionReleaseResponse() {
        return new WinActionReleaseResponse();
    }

}
