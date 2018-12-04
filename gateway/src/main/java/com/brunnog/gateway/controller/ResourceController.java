package com.brunnog.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunnog.gateway.dto.request.ResourceRequestDTO;
import com.brunnog.gateway.services.ResourceService;

import io.swagger.annotations.Api;
import sgs.architecture.rest.services.controller.CrudController;

@RestController
@Api(tags="Resource")
@RequestMapping("/resources")
public class ResourceController implements CrudController<ResourceRequestDTO> {
	
	@Autowired
	private ResourceService resourceService;

	@Override
	public ResourceService getService() {
		return resourceService;
	}
}