package com.pusulait.airsqreen.service.system9;

import com.pusulait.airsqreen.domain.enums.PlatformType;
import com.pusulait.airsqreen.domain.enums.ServiceType;
import com.pusulait.airsqreen.domain.event.Sistem9PushEvent;
import com.pusulait.airsqreen.domain.integration.PlatformUser;
import com.pusulait.airsqreen.domain.security.user.User;
import com.pusulait.airsqreen.repository.plt161.PlatformUserRepository;
import com.sistemdokuzmedya.ObjectFactory;
import com.sistemdokuzmedya.S9WExSrvc;
import com.sistemdokuzmedya.WinActionRelease;
import com.sistemdokuzmedya.WinActionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by benan on 7/13/2017.
 */
@Slf4j
@Service
public class Sistem9Adapter extends WebServiceGatewaySupport {

    @Inject
    private Environment env;

    @Autowired
    private PlatformUserRepository platformUserRepository;


    private String apiUrl;
    private String appId;
    private String sourceSys;

    private WebServiceTemplate webServiceTemplate;
    private S9WExSrvc s9WExSrvc;
    private WinActionRelease winActionRelease;
    private ObjectFactory objectFactory;

    //@Inject
    private Jaxb2Marshaller marshaller;

    @PostConstruct
    public void init() {

        this.apiUrl = env.getProperty("sistem9.api.url");
        this.appId = env.getProperty("sistem9.api.application_id");
        this.sourceSys = env.getProperty("sistem9.api.source_system");
        this.setDefaultUri(apiUrl);

    }

    protected void initJaxbMarshaller() {
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        webServiceTemplate = getWebServiceTemplate();
    }


    public void pushEvent(Sistem9PushEvent event) throws Exception {

        WinActionRelease winActionRelease = objectFactory.createWinActionRelease();

        winActionRelease.setActionID(event.getActionId());
        winActionRelease.setDeviceID(event.getDeviceId().intValue());

        PlatformUser platformUser = event.getDevice().getPlatformUser();

        if (platformUser != null && platformUser.getPlatformType().equals(PlatformType.SSP) && platformUser.getServiceType().equals(ServiceType.SISTEM_9) ) {

            winActionRelease.setPassword(platformUser.getPassword());
            winActionRelease.setUserName(platformUser.getUsername());
        } else {
            throw new Exception("Sistem 9 tanımlı user yok Tanımlı User Yok");
        }

        String response = s9WExSrvc.getS9WExSrvcSoap12().winActionRelease(winActionRelease.getUserName(), winActionRelease.getPassword(), winActionRelease.getActionID(),
                winActionRelease.getDeviceID());

    }
}
