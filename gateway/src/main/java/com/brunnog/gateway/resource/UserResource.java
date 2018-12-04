package com.brunnog.gateway.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.brunnog.gateway.controller.AuthorityController;
import com.brunnog.gateway.controller.UserController;
import com.brunnog.gateway.model.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserResource extends ResourceSupport {
	
	private User user;
	
	public UserResource(final User user) {
		this.user = user;
		Long id = user.getId();
		
		add(linkTo(UserController.class).withRel("users"));		
		add(linkTo(methodOn(AuthorityController.class).findByUserId(id)).withRel("authorities"));
		add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
	}
}