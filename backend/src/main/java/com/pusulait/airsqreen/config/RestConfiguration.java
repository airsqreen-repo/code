package com.pusulait.airsqreen.config;

import com.pusulait.airsqreen.domain.security.user.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class RestConfiguration extends RepositoryRestMvcConfiguration {
    private static Logger logger = Logger.getLogger(RestConfiguration.class);

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        try {

            super.configureRepositoryRestConfiguration(config);
            config.setBaseUri(new URI("/api"));
            config.exposeIdsFor(User.class, UserRole.class, Role.class, RoleRight.class, Right.class);

            config.setReturnBodyOnCreate(true);
            config.setReturnBodyOnUpdate(true);

        } catch (URISyntaxException e) {
            logger.error("failed to set base uri", e);
        }
    }


    @Override
    @Bean
    public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
        HateoasPageableHandlerMethodArgumentResolver resolver = super.pageableResolver();
        resolver.setOneIndexedParameters(true);
        return resolver;
    }

    @Override
    protected void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
//        validatingListener.addValidator("beforeCreate", new AccountValidator());
    }

    @Override
    public void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {

        ArrayList<MediaType> halList = new ArrayList<MediaType>();
        halList.add(MediaTypes.HAL_JSON);

        ArrayList<MediaType> appList = new ArrayList<MediaType>();
        appList.add(MediaType.APPLICATION_JSON);

            ((MappingJackson2HttpMessageConverter) messageConverters.get(0))
            .setSupportedMediaTypes(halList);
        ((MappingJackson2HttpMessageConverter) messageConverters.get(2))
            .setSupportedMediaTypes(appList);
    }

}





