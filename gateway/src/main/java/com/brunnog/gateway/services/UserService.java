package com.brunnog.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brunnog.gateway.dto.request.UserRequestDTO;
import com.brunnog.gateway.model.User;
import com.brunnog.gateway.repository.UserRepository;

import sgs.architecture.rest.services.service.CrudService;

@Service
public class UserService implements CrudService<User, UserRequestDTO> {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public UserRepository getRepository() {
		return userRepository;
	}	

	@Override
	public User save(UserRequestDTO user) throws NotFoundException {
		User newUser = new User();
		newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		newUser.setUsername(user.getUsername());
		
		return userRepository.save(newUser);
	}

	@Override
	public User update(Long id, UserRequestDTO user) throws NotFoundException {		
		User userToUpdate = findById(id);
		userToUpdate.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userToUpdate.setUsername(user.getUsername());
		
		return userRepository.save(userToUpdate);
	}
}
