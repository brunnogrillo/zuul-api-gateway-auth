package com.brunnog.gateway.dto;

import static com.brunnog.gateway.utils.UtilAuth.generateAuthority;
import static com.brunnog.gateway.utils.UtilAuth.getAuthorityFromString;

import java.io.IOException;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import com.brunnog.gateway.exception.AuthorizationFilterException;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthorityDTO implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	public AuthorityDTO() {}
	
	public AuthorityDTO(String authority) {
		try {
			AuthorityDTO auth = getAuthorityFromString(authority);
			this.resource = auth.getResource();
			this.method = auth.getMethod();
		} catch (IOException e) {
			throw new AuthorizationFilterException("Could not parse authority from string to dto", e);
		}
	}
	
	public AuthorityDTO(String resource, HttpMethod method) {
		this.resource = resource;
		this.method = method;
	}

	private String resource;
	private HttpMethod method;
	
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	@Override
	@JsonIgnore
	public String getAuthority() {
		return generateAuthority(this);
	}
}