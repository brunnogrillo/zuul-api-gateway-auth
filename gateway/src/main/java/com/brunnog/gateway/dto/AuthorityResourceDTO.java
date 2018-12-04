package com.brunnog.gateway.dto;

import com.brunnog.gateway.model.Resource;
import com.brunnog.gateway.model.User;

import lombok.Value;

@Value
public class AuthorityResourceDTO {
	
	private Resource resource;
	private User user;
}