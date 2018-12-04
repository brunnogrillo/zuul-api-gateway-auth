package com.brunnog.gateway.resource;

import static com.brunnog.gateway.utils.UtilAuth.linkToApplicationName;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpMethod;

import com.brunnog.gateway.controller.AuthorityController;
import com.brunnog.gateway.model.Authority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthorityServicesResource extends ResourceSupport {
	
	private HttpMethod method;
		
	public AuthorityServicesResource(Authority authority) {
		this.method = authority.getResource().getMethod();		
		
		add(linkToApplicationName(authority.getResource()));		
		add(linkTo(methodOn(AuthorityController.class).findById(authority.getId())).withSelfRel());
	}
}
