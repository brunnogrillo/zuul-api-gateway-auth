package com.brunnog.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brunnog.gateway.dto.request.ServerRequestDTO;
import com.brunnog.gateway.model.Server;
import com.brunnog.gateway.repository.ServerRepository;

import sgs.architecture.rest.services.service.CrudService;

@Service
public class ServerService implements CrudService<Server, ServerRequestDTO> {

	@Autowired
	private ServerRepository serverRepository;
	
	@Override
	public ServerRepository getRepository() {
		return serverRepository;
	}
}