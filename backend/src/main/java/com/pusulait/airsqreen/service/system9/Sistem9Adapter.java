package com.pusulait.airsqreen.service.system9;

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
    WinActionTemplate port;
    //Header header;

    @Inject
    private Jaxb2Marshaller marshaller;

    @PostConstruct
    public void init() {
        this.apiUrl = env.getProperty("sistem9.api.url");
        this.username = env.getProperty("sistem9.api.username");
        this.password = env.getProperty("sistem9.api.password");
        this.appId = env.getProperty("sistem9.api.application_id");
        this.sourceSys = env.getProperty("sistem9.api.source_system");
        this.setDefaultUri(apiUrl);
        initJaxbMarshaller();

    }

    protected void initJaxbMarshaller() {
        this.setMarshaller(marshaller);
        this.setUnmarshaller(marshaller);
        webServiceTemplate = getWebServiceTemplate();
        /*ServiceFactory<SiebelServices, SiebelServicesPortType> serviceFactory = new ServiceFactory<SiebelServices, SiebelServicesPortType>();
        serviceFactory.setCachePort(false);
        port = serviceFactory.getPort(SiebelServices.class, SiebelServicesPortType.class, apiUrl);

        header = new Header();
        //header.setRequestId("WEB-000000" + RandomUtil.generateTransactionId());
        header.setSourceSystem(sourceSys);
        header.setReplyExpected("Now");

        WinActionTemplate credentials = new Credentials();
        credentials.setActionID(appId);
        credentials.setUserName(username);
        credentials.setPassword(password);
        header.getCredentials().add(credentials);*/
    }
}
