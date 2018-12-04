package com.brunnog.gateway.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.brunnog.gateway.resource.ServerResource;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import sgs.architecture.rest.services.entity.IEntity;

@Entity
@Table(uniqueConstraints = 
	@UniqueConstraint(columnNames = "name")
)
@Data
public class Server implements IEntity<ServerResource> {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String uri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "server", fetch = FetchType.EAGER)
	private List<Authority> authority;

	@Override
	public ServerResource toResource() {
		return new ServerResource(this);
	}
}
