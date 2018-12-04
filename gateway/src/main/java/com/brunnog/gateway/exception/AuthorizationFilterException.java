package com.brunnog.gateway.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthorizationFilterException extends AuthenticationException {

	private static final long serialVersionUID = -8774830136556169641L;

	public AuthorizationFilterException(String msg, Throwable t) {
		super(msg, t);
	}

}
