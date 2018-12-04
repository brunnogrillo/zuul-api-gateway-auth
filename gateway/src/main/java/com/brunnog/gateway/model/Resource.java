package com.brunnog.gateway.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.http.HttpMethod;

import com.brunnog.gateway.resource.ResourceResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import sgs.architecture.rest.services.entity.IEntity;

@Entity
@Table(uniqueConstraints = 
	@UniqueConstraint(columnNames = {"service", "path", "method"})
)
@Data
public class Resource implements IEntity<ResourceResource> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String service;
	
	private String path;
	
	@Enumerated(EnumType.STRING)
	private HttpMethod method;
	
	@JsonIgnore
	@OneToMany(mappedBy = "resource", fetch = FetchType.EAGER)
	private List<Authority> authority;
		
	public String getEndpoint() {
		return service.concat(path);
	}

	@Override
	public ResourceResource toResource() {
		return new ResourceResource(this);
	}
}
