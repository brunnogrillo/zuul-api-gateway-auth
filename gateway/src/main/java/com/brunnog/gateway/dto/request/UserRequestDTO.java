package com.brunnog.gateway.dto.request;

import com.brunnog.gateway.model.User;

import lombok.EqualsAndHashCode;
import lombok.Value;
import sgs.architecture.rest.services.dto.request.IRequestDTO;

@Value
@EqualsAndHashCode(callSuper = false)
public class UserRequestDTO implements IRequestDTO<User> {
	
	private String username;
	private String password;
}
