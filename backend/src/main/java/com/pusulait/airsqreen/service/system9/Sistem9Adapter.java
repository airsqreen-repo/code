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
import javax.naming.MalformedLinkException;
import javax.xml.ws.soap.AddressingFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Created by benan on 7/13/2017.
 */
@Slf4j
@Service
public class Sistem9Adapter {

    @Inject
    private Environment env;

    @Autowired
    private PlatformUserRepository platformUserRepository;

    private String apiUrl;
    S9WExSrvc service = null;

    @PostConstruct
    public void init() throws MalformedURLException {

        this.apiUrl = env.getProperty("sistem9.api.url");
        service = new S9WExSrvc(new URL(apiUrl));
    }


    public void pushEvent(Sistem9PushEvent event) throws Exception {

        if (event != null) {

            WinActionTemplate winActionTemplate = new WinActionTemplate();
            winActionTemplate.setActionID(event.getActionId());
            winActionTemplate.setDeviceID(event.getDeviceId().intValue());

            PlatformUser platformUser = event.getDevice().getPlatformUser();

            if (platformUser != null && platformUser.getPlatformType().equals(PlatformType.SSP) && platformUser.getServiceType().equals(ServiceType.SISTEM_9)) {

                winActionTemplate.setPassword(platformUser.getPassword());
                winActionTemplate.setUserName(platformUser.getUsername());
            } else {
                throw new Exception("Sistem 9 tanımlı user yok Tanımlı User Yok");
            }
            String response = service.getS9WExSrvcSoap().winActionTemplate(winActionTemplate.getUserName(), winActionTemplate.getPassword(), winActionTemplate.getActionID(), winActionTemplate.getDeviceID());
        }
    }
}
