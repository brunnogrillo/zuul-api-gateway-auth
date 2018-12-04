package com.brunnog.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunnog.gateway.model.Server;

public interface ServerRepository extends JpaRepository<Server, Long> {
	
}
