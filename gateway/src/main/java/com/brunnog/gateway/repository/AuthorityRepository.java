package com.brunnog.gateway.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brunnog.gateway.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<List<Authority>> findByUserId(Long id);
	
	Optional<List<Authority>> findByResourceServiceAndUserUsername(String serviceName, String username);
}
