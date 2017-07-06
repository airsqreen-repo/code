package com.pusulait.airsqreen.service.exceptions;

/**
 * Created by TRB on 06.06.2017.
 */
import org.springframework.security.core.AuthenticationException;

/**
 * This exception is throw in case of a not activated user trying to authenticate.
 */
public class UserNotExistException extends AuthenticationException {

    public UserNotExistException(String message) {
        super(message);
    }

    public UserNotExistException(String message, Throwable t) {
        super(message, t);
    }
}
