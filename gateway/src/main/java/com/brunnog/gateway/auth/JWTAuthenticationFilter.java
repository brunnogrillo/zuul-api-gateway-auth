package com.brunnog.gateway.auth;

import static com.brunnog.gateway.resource.LoginResource.resource;
import static com.brunnog.gateway.utils.UtilAuthority.authorityToDTO;
import static com.brunnog.gateway.utils.UtilAuthority.groupAuthoritiesByService;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static sgs.architecture.rest.services.utils.UtilHateoas.resourceToHalJson;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brunnog.gateway.config.JwtAuthenticationConfig;
import com.brunnog.gateway.dto.AuthorityDTO;
import com.brunnog.gateway.exception.AuthenticationFilterException;
import com.brunnog.gateway.model.User;
import com.brunnog.gateway.resource.LoginResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JwtAuthenticationConfig config;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtAuthenticationConfig config) {
		this.authenticationManager = authenticationManager;
		this.config = config;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

		try {
			User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword(), user.getAuthorities());

			return authenticationManager.authenticate(authentication);
		} catch (IOException e) {
			log.error("Authentication failed", e);
			throw new AuthenticationFilterException("Authentication failed", e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) {
		
		User principal = (User) auth.getPrincipal();		
		String authorities = getAuthorities(principal);		
		String username = principal.getUsername();
		String token = getToken(authorities, username);			
		
		Set<String> services = groupAuthoritiesByService(principal);
		List<LoginResource> access = services.stream().map(s -> resource(s, username)).collect(toList());		
		
		res.addHeader(config.getHeader(), config.getPrefix() + token);
		res.addHeader(CONTENT_TYPE, "application/hal+json;charset=UTF-8");
		try {
			res.getWriter().write(resourceToHalJson(access));
		} catch (IOException e) {
			log.error("Could not parse Resource to Json HAL", e);
			throw new AuthenticationFilterException("Could not parse Resource to Json HAL", e);
		}		
	}

	private String getAuthorities(User principal) {		
		try {
			List<AuthorityDTO> authority = authorityToDTO(principal);
			return new ObjectMapper().writeValueAsString(authority);
		} catch (JsonProcessingException e) {
			log.error("Could not parse authority to string", e);
			throw new AuthenticationFilterException("Could not parse authority to string", e);
		}
	}

	private String getToken(String authorities, String username) {
		return Jwts.builder()
				   .setSubject(username)
			       .claim(config.getAuthorityKey(), authorities)
				   .setExpiration(new Date(System.currentTimeMillis() + config.getExpiration()))
				   .signWith(HS512, config.getSecret())
				   .compact();
	}
}
