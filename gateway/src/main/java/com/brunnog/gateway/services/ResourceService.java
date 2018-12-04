package com.brunnog.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brunnog.gateway.dto.request.ResourceRequestDTO;
import com.brunnog.gateway.model.Resource;
import com.brunnog.gateway.repository.ResourceRepository;

import sgs.architecture.rest.services.service.CrudService;

@Service
public class ResourceService implements CrudService<Resource, ResourceRequestDTO> {

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Override
	public ResourceRepository getRepository() {
		return resourceRepository;
	}
}