package com.brunnog.gateway.resource;

import static com.brunnog.gateway.utils.UtilAuth.linkToApplicationName;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.brunnog.gateway.controller.ResourceController;
import com.brunnog.gateway.model.Resource;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResourceResource extends ResourceSupport {
		
	private Resource resource;
	
	public ResourceResource(Resource resource) {
		this.resource = resource;
		
		add(linkToApplicationName(resource));
		add(linkTo(ResourceController.class).withRel("resources"));
		add(linkTo(methodOn(ResourceController.class).findById(resource.getId())).withSelfRel());
	}
}