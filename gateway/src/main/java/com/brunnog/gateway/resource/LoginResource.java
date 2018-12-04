package com.brunnog.gateway.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.brunnog.gateway.controller.AuthorityController;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginResource extends ResourceSupport {
	
	public static LoginResource resource(String service, String username) {
		return new LoginResource(service, username);
	}
	
	private LoginResource(String service, String username) {
		this.service = service;
		add(linkTo(methodOn(AuthorityController.class).findByServiceName(service, username)).withRel(service));
	}
	
	private String service;	
}