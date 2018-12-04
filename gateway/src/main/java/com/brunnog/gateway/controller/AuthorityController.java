package com.brunnog.gateway.controller;

import static com.brunnog.gateway.utils.UtilAuth.getClaims;
import static java.util.stream.Collectors.toList;
import static sgs.architecture.rest.services.utils.UtilHateoas.toResources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brunnog.gateway.config.JwtAuthenticationConfig;
import com.brunnog.gateway.dto.request.AuthorityRequestDTO;
import com.brunnog.gateway.model.Authority;
import com.brunnog.gateway.resource.AuthorityResource;
import com.brunnog.gateway.resource.AuthorityServicesResource;
import com.brunnog.gateway.services.AuthorityService;

import io.swagger.annotations.Api;
import sgs.architecture.rest.services.annotation.AuthorizationHeader;
import sgs.architecture.rest.services.controller.CrudController;

@RestController
@Api(tags="Authority")
@RequestMapping("/authorities")
public class AuthorityController implements CrudController<AuthorityRequestDTO> {

	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private JwtAuthenticationConfig jwtConfig;
	
	@Override
	public AuthorityService getService() {
		return authorityService;
	}
	
	@AuthorizationHeader
	@GetMapping("/services")
	public ResponseEntity<Resources<AuthorityServicesResource>> findByServiceName(
			@RequestParam("name") String serviceName,
			@RequestParam("username") String username) {
		try {			
			String user = getClaims(httpServletRequest, jwtConfig).getSubject();			
			if (!user.equals(username))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			
			List<AuthorityServicesResource> authorities = authorityService
					.findByServiceAndUsername(serviceName, username)
					.stream()
					.map(Authority::toAuthorityServicesResource)
					.collect(toList());
			
			return ResponseEntity.ok(toResources(authorities));
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@AuthorizationHeader
	@GetMapping("/user/{userId}")
	public ResponseEntity<Resources<AuthorityResource>> findByUserId(@PathVariable("userId") Long id) {
		try {
			List<AuthorityResource> authorities = authorityService.findByUserId(id).stream().map(Authority::toResource).collect(toList());
			return ResponseEntity.ok(toResources(authorities));
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
