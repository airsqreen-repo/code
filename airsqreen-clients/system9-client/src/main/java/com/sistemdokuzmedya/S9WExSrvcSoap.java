package com.sistemdokuzmedya;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.7
 * 2017-07-15T18:38:29.895+09:00
 * Generated source version: 3.1.7
 * 
 */
@WebService(targetNamespace = "http://www.sistemdokuzmedya.com/", name = "S9_WExSrvcSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface S9WExSrvcSoap {

    /**
     * Action_Template
     */
    @WebMethod(operationName = "Win_Action_Template", action = "http://www.sistemdokuzmedya.com/Win_Action_Template")
    @RequestWrapper(localName = "Win_Action_Template", targetNamespace = "http://www.sistemdokuzmedya.com/", className = "com.sistemdokuzmedya.WinActionTemplate")
    @ResponseWrapper(localName = "Win_Action_TemplateResponse", targetNamespace = "http://www.sistemdokuzmedya.com/", className = "com.sistemdokuzmedya.WinActionTemplateResponse")
    @WebResult(name = "Win_Action_TemplateResult", targetNamespace = "http://www.sistemdokuzmedya.com/")
    public java.lang.String winActionTemplate(
        @WebParam(name = "UserName_", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String userName,
        @WebParam(name = "Password", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String password,
        @WebParam(name = "Action_ID", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String actionID,
        @WebParam(name = "Device_ID", targetNamespace = "http://www.sistemdokuzmedya.com/")
        int deviceID
    );

    /**
     * Action_Template
     */
    @WebMethod(operationName = "Win_Action_Release", action = "http://www.sistemdokuzmedya.com/Win_Action_Release")
    @RequestWrapper(localName = "Win_Action_Release", targetNamespace = "http://www.sistemdokuzmedya.com/", className = "com.sistemdokuzmedya.WinActionRelease")
    @ResponseWrapper(localName = "Win_Action_ReleaseResponse", targetNamespace = "http://www.sistemdokuzmedya.com/", className = "com.sistemdokuzmedya.WinActionReleaseResponse")
    @WebResult(name = "Win_Action_ReleaseResult", targetNamespace = "http://www.sistemdokuzmedya.com/")
    public java.lang.String winActionRelease(
        @WebParam(name = "UserName_", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String userName,
        @WebParam(name = "Password", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String password,
        @WebParam(name = "Action_ID", targetNamespace = "http://www.sistemdokuzmedya.com/")
        java.lang.String actionID,
        @WebParam(name = "Device_ID", targetNamespace = "http://www.sistemdokuzmedya.com/")
        int deviceID
    );
}
