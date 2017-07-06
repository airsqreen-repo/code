package com.pusulait.airsqreen.service.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is throw in case of a not activated user trying to authenticate.
 */
public class EmailAlreadyInUseException extends AuthenticationException {

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    public EmailAlreadyInUseException(String message, Throwable t) {
        super(message, t);
    }
}
