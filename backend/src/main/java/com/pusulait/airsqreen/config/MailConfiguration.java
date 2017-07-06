package com.pusulait.airsqreen.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(MailConfiguration.class);

    @Value("${mail.ENV_SPRING_MAIL}")
    private String ENV_SPRING_MAIL;

    @Value("${mail.defaultHost}")
    private String DEFAULT_HOST;

    @Value("${mail.PROP_HOST}")
    private String PROP_HOST;

    @Value("${mail.defaultPropHost}")
    private String DEFAULT_PROP_HOST;

    @Value("${mail.PROP_PORT}")
    private String PROP_PORT;

    @Value("${mail.propUser}")
    private String user;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.PROP_PROTO}")
    private String protocol;

    @Value("${mail.PROP_TLS}")
    private String PROP_TLS;

    @Value("${mail.PROP_AUTH}")
    private String PROP_AUTH;

    @Value("${mail.PROP_SMTP_AUTH}")
    private String PROP_SMTP_AUTH;

    @Value("${mail.PROP_STARTTLS}")
    private String PROP_STARTTLS;

    @Value("${mail.transport.protocol}")
    private String PROP_TRANSPORT_PROTO;


    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_SPRING_MAIL);
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        log.debug("Configuring mail server");
        String host = (PROP_HOST != null) ? PROP_HOST : DEFAULT_PROP_HOST;
        int port = Integer.valueOf(PROP_PORT);
        Boolean tls = Boolean.valueOf(PROP_TLS);
        Boolean auth = Boolean.valueOf(PROP_AUTH);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isEmpty()) {
            sender.setHost(host);
        } else {
            log.warn("Warning! Your SMTP server is not configured. We will try to use one on localhost.");
            log.debug("Did you configure your SMTP settings in your application.yml?");
            sender.setHost(DEFAULT_HOST);
        }
        sender.setPort(port);
        sender.setUsername(user);
        sender.setPassword(password);

        Properties sendProperties = new Properties();
        sendProperties.setProperty(PROP_SMTP_AUTH, auth.toString());
        sendProperties.setProperty(PROP_STARTTLS, tls.toString());
        sendProperties.setProperty(PROP_TRANSPORT_PROTO, protocol);
        sendProperties.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(sendProperties);
        return sender;
    }
}
