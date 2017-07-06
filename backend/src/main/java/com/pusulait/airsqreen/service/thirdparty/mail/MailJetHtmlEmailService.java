package com.pusulait.airsqreen.service.thirdparty.mail;

import com.pusulait.airsqreen.domain.security.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import java.util.Locale;

/**
 * Created by ferhatyaban on 03.07.2017.
 */

@Service
@Slf4j
public class MailJetHtmlEmailService extends MailJetEmailService {

    private static final String USER = "user";

    private static final String SYSTEM_MESSAGE = "systemMessage";

    private static final String BIRTH_PLAN = "birthPlan";

    private static final String BASE_URL = "baseUrl";

    @Inject
    private MessageSource messageSource;

    @Autowired
    private TemplateEngine templateEngine;

    public MailJetEmailStatus send(String to, String subject, String templateName, Context context) throws Exception {
        String body = templateEngine.process(templateName, context);
        return send(to, subject, body);
    }

    @Async
    public void sendPasswordResetMail(User user) throws Exception {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLanguage().getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, this.config.getBaseUrl());
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        send(user.getEmail(), subject, "passwordResetEmail", context );
    }


    @Async
    public void sendActivationEmail(User user) throws Exception {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale =Locale.forLanguageTag(user.getLanguage().getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, this.config.getBaseUrl());
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        send(user.getEmail(), subject, "activationEmail", context );
    }



}