package com.brunnog.gateway.utils;

import static com.brunnog.gateway.utils.UtilAuth.isResourceValid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import com.brunnog.gateway.config.JwtAuthenticationConfig;
import com.brunnog.gateway.dto.AuthorityDTO;

public class UtilAuthTest {
	
	@Before
	public void getConfig() {
		config = new JwtAuthenticationConfig();
		config.setApiPrefix("/api");
	}
	
	private JwtAuthenticationConfig config;
	
	@Test
	public void containsAuthority() {
		
		AuthorityDTO auth = new AuthorityDTO("test", HttpMethod.GET);
		List<GrantedAuthority> grantedAuthorities = Arrays.asList(auth);
		
		Boolean containsAuthority = UtilAuth.contains(grantedAuthorities, "/test", "GET", config);
		assertTrue(containsAuthority);
	}
	
	@Test
	public void notContainsAuthority() {
		
		AuthorityDTO auth1 = new AuthorityDTO("test", HttpMethod.POST);
		AuthorityDTO auth2 = new AuthorityDTO("test1", HttpMethod.GET);
		List<GrantedAuthority> grantedAuthorities = Arrays.asList(auth1, auth2);
		
		Boolean containsAuthority = UtilAuth.contains(grantedAuthorities, "/test/", "GET", config);
		assertFalse(containsAuthority);
	}
	
	@Test
	public void notContainsWithPathAuthority() {
		
		AuthorityDTO auth1 = new AuthorityDTO("test/*", HttpMethod.POST);
		List<GrantedAuthority> grantedAuthorities = Arrays.asList(auth1);
		
		Boolean containsAuthority = UtilAuth.contains(grantedAuthorities, "/test/1", "GET", config);
		assertFalse(containsAuthority);
	}
	
	@Test
	public void notContainsWithQueryAuthority() {
		
		AuthorityDTO auth1 = new AuthorityDTO("test", HttpMethod.POST);
		List<GrantedAuthority> grantedAuthorities = Arrays.asList(auth1);
		
		Boolean containsAuthority = UtilAuth.contains(grantedAuthorities, "/test?id=1", "GET", config);
		assertFalse(containsAuthority);
	}
	
	@Test
	public void containsPathParameter() {
		assertFalse(isResourceValid("/inicio/*/fim/*", "/inicio/123123/fim/111/222/333"));
		
		assertTrue(isResourceValid("/*/inicio/fim", "/1/inicio/fim/"));
		assertTrue(isResourceValid("inicio/*/fim", "/inicio/1/fim/"));
		assertTrue(isResourceValid("/inicio/fim/*/", "/inicio/fim/1/"));
		
		assertTrue(isResourceValid("/*/inicio/*/fim", "/1/inicio/123/fim/"));
		assertTrue(isResourceValid("inicio/*/fim/*/", "/inicio/1/fim/123/"));
		assertTrue(isResourceValid("/*/inicio/fim/*/", "/123/inicio/fim/1/"));
						
		assertTrue(isResourceValid("inicio/fim", "/inicio/fim/"));		
		assertTrue(isResourceValid("/inicio/fim/", "/inicio/fim/"));
		
		assertFalse(isResourceValid("/inicio1/*/fim/*", "/inicio/1235235/fim/1"));
		assertFalse(isResourceValid("/inicio/*/fim/*", "/inicio/fim/1"));		
		
		assertFalse(isResourceValid("/inicio/*/fim/", "/inicio/654987/654987/321/fim/"));
		assertFalse(isResourceValid("/inicio/*fim/", "/inicio/35468798fim/"));
		assertFalse(isResourceValid("*", "inicio/meio/fim/321654897/"));
		
		assertFalse(isResourceValid("*/*/inicio/inicio111/*/111/*/fim", "inicio/111fim/1"));
		
		assertFalse(isResourceValid("/user/*/authority", "/user/"));
	}
	
	@Test
	public void containsQueryParameter() {
		assertTrue(isResourceValid("inicio", "/inicio?id=5"));
		assertTrue(isResourceValid("inicio/", "/inicio?id=5"));
		assertTrue(isResourceValid("/inicio", "/inicio?id=5"));
		assertTrue(isResourceValid("/inicio/", "/inicio?id=5"));
		assertTrue(isResourceValid("/inicio/", "/inicio?id=5&name=test"));
		
		assertFalse(isResourceValid("/invalid/", "/inicio?id=5&name=test"));
	}
}