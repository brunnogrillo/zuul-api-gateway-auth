package com.brunnog.gateway.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationFilterException extends AuthenticationException {

	private static final long serialVersionUID = -8774830136556169641L;

	public AuthenticationFilterException(String msg, Throwable t) {
		super(msg, t);
	}
}
