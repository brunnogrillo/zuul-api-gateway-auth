package com.brunnog.gateway.utils;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import com.brunnog.gateway.config.JwtAuthenticationConfig;
import com.brunnog.gateway.controller.GatewayController;
import com.brunnog.gateway.dto.AuthorityDTO;
import com.brunnog.gateway.exception.AuthenticationFilterException;
import com.brunnog.gateway.model.Resource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class UtilAuth {
	
	private UtilAuth() {
		throw new IllegalStateException("Utility class");
	}
		
	private static final String SLASH = "/";
	
	public static Claims getClaims(HttpServletRequest req, JwtAuthenticationConfig config) {
		String token = req.getHeader(config.getHeader());
		return Jwts.parser()
				.setSigningKey(config.getSecret())
				.parseClaimsJws(token.replace(config.getPrefix(), EMPTY))
				.getBody();
	}

	public static Boolean contains(Collection<GrantedAuthority> grantedAuthorities, final String uri, final String method, JwtAuthenticationConfig config) {		
				
		List<AuthorityDTO> authorities = grantedAuthorities
				.stream()
				.map(a -> ((AuthorityDTO) a))
				.collect(toList());		
		
		String newUri = uri.replace(config.getApiPrefix(), EMPTY).concat(SLASH);
		for (AuthorityDTO auth : authorities) {
			if (auth.getMethod().matches(method) && isResourceValid(auth.getResource(), newUri))
				return TRUE;
		}
		return FALSE;
	}
	
	protected static boolean isResourceValid(String resource, String uri) {
		
		if (!resource.startsWith(SLASH)) resource = SLASH.concat(resource);
		if (!resource.endsWith(SLASH)) resource += SLASH;		
		
		if (uri.equals(resource))
			return true;		
		else if (resource.contains("*"))
			return containsPathInUrl(resource, uri);
		else if (uri.contains("?"))
			return containsQueryInUrl(resource, uri);
		else
			return false;		
	}
		
	private static boolean containsQueryInUrl(String resource, String uri) {		
		uri = uri.split("\\?")[0] + "/";		
		return resource.equals(uri);
	}
	
	private static Boolean containsPathInUrl(String authority, String uri) {
		int indexStar = authority.indexOf("/*");

		if (indexStar == -1) 
			return authority.equals(uri);
		
		String fisrtPartAuth = authority.substring(0, indexStar + 1);
		if (!uri.startsWith(fisrtPartAuth)) 
			return false;		

		String lastPartAuth = authority.substring(indexStar + 2, authority.length());
		String firstPartAuth = uri.substring(fisrtPartAuth.length(), uri.length());
		
		int firstPartAuthSlash = firstPartAuth.indexOf(SLASH);		
		firstPartAuth = firstPartAuth.substring(firstPartAuthSlash > -1 ? firstPartAuthSlash : 0, firstPartAuth.length());

		return containsPathInUrl(lastPartAuth, firstPartAuth);
	}
	
	public static AuthorityDTO getAuthorityFromString(String authority) throws IOException {
		return new ObjectMapper().readValue(authority, AuthorityDTO.class);
	}
	
	public static String generateAuthority(String resource, HttpMethod method) {		
		return generateAuthority(new AuthorityDTO(resource, method));
	}
	
	public static String generateAuthority(AuthorityDTO authorityDTO) {
		try {
			return new ObjectMapper().writeValueAsString(authorityDTO);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public static Link linkToApplicationName(Resource resource) {
		String path = resource.getPath();
		String serverName = resource.getServer().getName();
		
		if (getApplicationName().equals(resource.getServer().getName()))
			return linkTo(GatewayController.class).slash(path).withRel("endpoint");
		else
			return linkTo(GatewayController.class).slash(serverName).slash(path).withRel("endpoint");
	}
	
	public static String getApplicationName() {
		return getApplicationPropertiies().getProperty("spring.application.name");
	}
	
	private static Properties getApplicationPropertiies() {
		Properties configuration = new Properties();
		try (InputStream inputStream = UtilAuth.class.getClassLoader().getResourceAsStream("application.properties")) {
			configuration.load(inputStream);
		} catch (IOException e) {
			throw new AuthenticationFilterException("Could not load application.properties", e);
		}
		
		return configuration;
	}
}
