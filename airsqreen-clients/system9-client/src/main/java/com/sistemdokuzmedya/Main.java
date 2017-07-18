package com.sistemdokuzmedya;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bhdr on 18.07.2017.
 */
public class Main {

    public static void main(String[] args) throws MalformedURLException {

        URL url = new File("c:\\S9_WExSrvc.wsdl").toURI().toURL();


        S9WExSrvc service = new S9WExSrvc(url);

        // servis çağrısı
        String result = service.getS9WExSrvcSoap().winActionRelease("S9SERVICES",
                "SisT3mD0kuz",
                "?",
                0);


    }

}
