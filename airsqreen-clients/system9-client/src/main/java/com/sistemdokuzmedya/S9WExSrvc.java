package com.sistemdokuzmedya;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * Sistem Dokuz Medya A.Ş.
 *
 * This class was generated by Apache CXF 3.1.7
 * 2017-07-12T16:15:20.856+03:00
 * Generated source version: 3.1.7
 * 
 */
@WebServiceClient(name = "S9_WExSrvc", 
                  wsdlLocation = "classpath:wsdl/S9_WExSrvc.wsdl",
                  targetNamespace = "http://www.sistemdokuzmedya.com/") 
public class S9WExSrvc extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.sistemdokuzmedya.com/", "S9_WExSrvc");
    public final static QName S9WExSrvcSoap12 = new QName("http://www.sistemdokuzmedya.com/", "S9_WExSrvcSoap12");
    public final static QName S9WExSrvcSoap = new QName("http://www.sistemdokuzmedya.com/", "S9_WExSrvcSoap");
    static {
        URL url = S9WExSrvc.class.getClassLoader().getResource("wsdl/S9_WExSrvc.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(S9WExSrvc.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/S9_WExSrvc.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public S9WExSrvc(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public S9WExSrvc(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public S9WExSrvc() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public S9WExSrvc(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public S9WExSrvc(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public S9WExSrvc(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns S9WExSrvcSoap
     */
    @WebEndpoint(name = "S9_WExSrvcSoap12")
    public S9WExSrvcSoap getS9WExSrvcSoap12() {
        return super.getPort(S9WExSrvcSoap12, S9WExSrvcSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns S9WExSrvcSoap
     */
    @WebEndpoint(name = "S9_WExSrvcSoap12")
    public S9WExSrvcSoap getS9WExSrvcSoap12(WebServiceFeature... features) {
        return super.getPort(S9WExSrvcSoap12, S9WExSrvcSoap.class, features);
    }


    /**
     *
     * @return
     *     returns S9WExSrvcSoap
     */
    @WebEndpoint(name = "S9_WExSrvcSoap")
    public S9WExSrvcSoap getS9WExSrvcSoap() {
        return super.getPort(S9WExSrvcSoap, S9WExSrvcSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns S9WExSrvcSoap
     */
    @WebEndpoint(name = "S9_WExSrvcSoap")
    public S9WExSrvcSoap getS9WExSrvcSoap(WebServiceFeature... features) {
        return super.getPort(S9WExSrvcSoap, S9WExSrvcSoap.class, features);
    }

}
