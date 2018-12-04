package com.brunnog.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.brunnog.gateway.dto.request.ResourceRequestDTO;
import com.brunnog.gateway.model.Resource;
import com.brunnog.gateway.model.Server;
import com.brunnog.gateway.repository.ResourceRepository;
import com.brunnog.gateway.repository.ServerRepository;

import sgs.architecture.rest.services.service.CrudService;

@Service
public class ResourceService implements CrudService<Resource, ResourceRequestDTO> {

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ServerRepository serverRepository;
	
	@Override
	public ResourceRepository getRepository() {
		return resourceRepository;
	}
	
	@Override
	public Resource save(ResourceRequestDTO resource) throws NotFoundException {
		
		Server server = serverRepository.findById(resource.getServerId()).orElseThrow(NotFoundException::new);
		
		Resource resourceTosave = new Resource();
		resourceTosave.setMethod(resource.getMethod());
		resourceTosave.setPath(resource.getPath());
		resourceTosave.setServer(server);
		
		return getRepository().save(resourceTosave);
	}
	
	@Override
	public Resource update(Long id, ResourceRequestDTO resource) throws NotFoundException {		
		Resource resourceToUpdate = findById(id);
		Server server = serverRepository.findById(resource.getServerId()).orElseThrow(NotFoundException::new);
		
		resourceToUpdate.setMethod(resource.getMethod());
		resourceToUpdate.setPath(resource.getPath());
		resourceToUpdate.setServer(server);
		
		return getRepository().save(resourceToUpdate);
	}
}