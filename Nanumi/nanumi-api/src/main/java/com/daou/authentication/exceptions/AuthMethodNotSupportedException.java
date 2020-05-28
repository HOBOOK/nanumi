package com.daou.authentication.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * @author pkh879
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    public AuthMethodNotSupportedException(String msg){
        super(msg);
    }
}
