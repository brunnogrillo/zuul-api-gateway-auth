package com.brunnog.gateway.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.brunnog.gateway.dto.request.AuthorityRequestDTO;
import com.brunnog.gateway.model.Authority;
import com.brunnog.gateway.model.Resource;
import com.brunnog.gateway.model.User;
import com.brunnog.gateway.repository.AuthorityRepository;

import sgs.architecture.rest.services.service.CrudService;

@Service
public class AuthorityService implements CrudService<Authority, AuthorityRequestDTO> {
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private ResourceService resourceService;

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public AuthorityRepository getRepository() {
		return authorityRepository;
	}

	@Override
	public Authority save(AuthorityRequestDTO authority) throws NotFoundException {
		
		User user = userService.findById(authority.getUserId());
		Resource resource = resourceService.findById(authority.getResourceId());
		
		Authority authToSave = new Authority(user, resource);
		
		return getRepository().save(authToSave);
	}

	@Override
	public Authority update(Long id, AuthorityRequestDTO authority) throws NotFoundException {		
		Authority authorityToUpdate = findById(id);
		User user = userService.findById(authority.getUserId());
		Resource resource = resourceService.findById(authority.getResourceId());
		
		authorityToUpdate.setUser(user);
		authorityToUpdate.setResource(resource);
		
		return getRepository().save(authorityToUpdate);
	}

	public List<Authority> findByUserId(Long id) throws NotFoundException {
		return getRepository().findByUserId(id).orElseThrow(NotFoundException::new);
	}

	public List<Authority> findByServiceAndUsername(String serverName, String username) throws NotFoundException {
		return getRepository().findByResourceServerNameAndUserUsername(serverName, username).orElseThrow(NotFoundException::new);
	}
}
