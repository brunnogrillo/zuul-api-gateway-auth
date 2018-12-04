package com.brunnog.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunnog.gateway.model.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
