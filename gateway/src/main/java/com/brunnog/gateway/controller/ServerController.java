package com.brunnog.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunnog.gateway.dto.request.ServerRequestDTO;
import com.brunnog.gateway.services.ServerService;

import io.swagger.annotations.Api;
import sgs.architecture.rest.services.controller.CrudController;

@RestController
@Api(tags="Server")
@RequestMapping("/servers")
public class ServerController implements CrudController<ServerRequestDTO> {

	@Autowired
	private ServerService serverService;
	
	@Override
	public ServerService getService() {
		return serverService;
	}
}
