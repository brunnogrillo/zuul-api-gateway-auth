package com.brunnog.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brunnog.gateway.dto.request.UserRequestDTO;
import com.brunnog.gateway.services.UserService;

import io.swagger.annotations.Api;
import sgs.architecture.rest.services.controller.CrudAuthorizationController;

@RestController
@Api(tags="User")
@RequestMapping("/users")
public class UserController implements CrudAuthorizationController<UserRequestDTO> {	
		
	@Autowired
	private UserService userService;

	@Override
	public UserService getService() {
		return userService;
	}
}
