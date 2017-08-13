package com.pusulait.airsqreen.service.system9;

import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.sistemdokuzmedya.ObjectFactory;
import com.sistemdokuzmedya.S9WExSrvc;
import com.sistemdokuzmedya.WinActionRelease;
import com.sistemdokuzmedya.WinActionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by benan on 7/13/2017.
 */
@Slf4j
@Service
public class Sistem9Adapter extends WebServiceGatewaySupport {

    @Inject
    private Environment env;

    private String apiUrl;
    private String username;
    private String password;
    private String appId;
    private String sourceSys;

    private WebServiceTemplate webServiceTemplate;
    private S9WExSrvc s9WExSrvc;
    private WinActionRelease winActionRelease;
    private ObjectFactory objectFactory;
    //Header header;

    //@Inject
    private Jaxb2Marshaller marshaller;

    @PostConstruct
    public void init() {
        this.apiUrl = env.getProperty("sistem9.api.url");
        this.username = env.getProperty("sistem9.api.username");
        this.password = env.getProperty("sistem9.api.password");
        this.appId = env.getProperty("sistem9.api.application_id");
        this.sourceSys = env.getProperty("sistem9.api.source_system");
        this.setDefaultUri(apiUrl);
        //  initJaxbMarshaller();

    }

    protected void initJaxbMarshaller() {
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        webServiceTemplate = getWebServiceTemplate();
    }


    public void push(Sistem9PushEvent event) {

        WinActionRelease winActionRelease = objectFactory.createWinActionRelease();

        winActionRelease.setActionID(event.getActionId());
        winActionRelease.setDeviceID(event.getDeviceId().intValue());
        winActionRelease.setPassword(event.getPlatformUser().getPassword());
        winActionRelease.setUserName(event.getPlatformUser().getUsername());

        String response = s9WExSrvc.getS9WExSrvcSoap12().winActionRelease(winActionRelease.getUserName(), winActionRelease.getPassword(), winActionRelease.getActionID(),
                winActionRelease.getDeviceID());
    }
}
