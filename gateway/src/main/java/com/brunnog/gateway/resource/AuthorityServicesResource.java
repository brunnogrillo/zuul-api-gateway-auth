package com.brunnog.gateway.resource;

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
	private String endpoint;
		
	public AuthorityServicesResource(Authority authority) {
		this.method = authority.getResource().getMethod();
		this.endpoint = authority.getServer().getUri().concat("/").concat(authority.getResource().getEndpoint());
		
		add(linkTo(methodOn(AuthorityController.class).findById(authority.getId())).withSelfRel());
	}
}
