package com.brunnog.gateway.model;

import static com.brunnog.gateway.utils.UtilAuth.generateAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

import com.brunnog.gateway.resource.AuthorityResource;
import com.brunnog.gateway.resource.AuthorityServicesResource;

import lombok.Data;
import sgs.architecture.rest.services.entity.IEntity;

@Entity
@Data
public class Authority implements GrantedAuthority, IEntity<AuthorityResource> {

	private static final long serialVersionUID = 1L;
	
	public Authority() {}

	public Authority(User user, Resource resource) {
		this.user = user;
		this.resource = resource;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne
	private Resource resource;
	
	@ManyToOne
	private User user;
	
	@Override
	public String getAuthority() {
		return generateAuthority(resource.getEndpoint(), resource.getMethod());
	}
	
	@Override
	public String toString() {
		return getAuthority();
	}

	@Override
	public AuthorityResource toResource() {
		return new AuthorityResource(this);
	}
	
	public AuthorityServicesResource toAuthorityServicesResource() {
		return new AuthorityServicesResource(this);
	}
}
