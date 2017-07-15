package com.pusulait.airsqreen.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by benan on 7/16/2017.
 */
public class RestPlatfrom161Util {

    public static HttpHeaders setHeader(String token) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        requestHeaders.set("PFM161-API-AccessToken", token);
        return requestHeaders;
    }
}
