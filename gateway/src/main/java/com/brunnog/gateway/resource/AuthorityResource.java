package com.brunnog.gateway.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.brunnog.gateway.controller.AuthorityController;
import com.brunnog.gateway.controller.ResourceController;
import com.brunnog.gateway.controller.UserController;
import com.brunnog.gateway.dto.AuthorityResourceDTO;
import com.brunnog.gateway.model.Authority;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthorityResource extends ResourceSupport {

	private AuthorityResourceDTO authority;
		
	public AuthorityResource(Authority authority) {
		this.authority = new AuthorityResourceDTO(authority.getResource(), authority.getUser());
		
		add(linkTo(AuthorityController.class).withRel("authorities"));
		add(linkTo(methodOn(ResourceController.class).findById(authority.getResource().getId())).withRel("resource"));
		add(linkTo(methodOn(UserController.class).findById(authority.getUser().getId())).withRel("user"));
		add(linkTo(methodOn(AuthorityController.class).findById(authority.getId())).withSelfRel());
	}
}