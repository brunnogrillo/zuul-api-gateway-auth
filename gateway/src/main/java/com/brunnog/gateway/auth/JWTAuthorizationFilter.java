package com.brunnog.gateway.auth;

import static com.brunnog.gateway.utils.UtilAuth.contains;
import static com.brunnog.gateway.utils.UtilAuth.getClaims;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.EMPTY;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.brunnog.gateway.config.JwtAuthenticationConfig;
import com.brunnog.gateway.dto.AuthorityDTO;
import com.brunnog.gateway.exception.AuthorizationFilterException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	
	private final JwtAuthenticationConfig jwtConfig;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JwtAuthenticationConfig jwtConfig) {
		super(authenticationManager);
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		
		if (isNull(authentication) || !contains(authentication.getAuthorities(), req.getRequestURI(), req.getMethod(), jwtConfig)) {
			SecurityContextHolder.getContext().setAuthentication(null);
			chain.doFilter(req, res);
			return;
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
		String token = req.getHeader(jwtConfig.getHeader());
		
		if (nonNull(token) && token.startsWith(jwtConfig.getPrefix())) {
			Claims claims = getClaims(req, jwtConfig);
					
			List<AuthorityDTO> authorities = Collections.emptyList();
			try {
				authorities = new ObjectMapper().readValue(claims.get(jwtConfig.getAuthorityKey()).toString(), new TypeReference<List<AuthorityDTO>>() {});
			} catch (IOException e) {
				throw new AuthorizationFilterException("Invalid authority", e);
			}

			String user = claims.getSubject();			
			if (nonNull(user)) 
				return new UsernamePasswordAuthenticationToken(user, EMPTY, authorities);			
		}
		
		return null;
	}
}
