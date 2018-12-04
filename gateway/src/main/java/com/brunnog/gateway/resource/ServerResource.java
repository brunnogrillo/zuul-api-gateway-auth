package com.brunnog.gateway.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.brunnog.gateway.controller.ServerController;
import com.brunnog.gateway.model.Server;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServerResource extends ResourceSupport {

	private Server server;
		
	public ServerResource(Server server) {
		this.server = server;
		
		add(linkTo(ServerController.class).withRel("servers"));
		add(linkTo(methodOn(ServerController.class).findById(server.getId())).withSelfRel());
	}
}
