package com.brunnog.gateway.utils;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.util.Strings;

import com.brunnog.gateway.dto.AuthorityDTO;
import com.brunnog.gateway.model.Authority;
import com.brunnog.gateway.model.Resource;
import com.brunnog.gateway.model.User;

public class UtilAuthority {
	
	private UtilAuthority() {
		throw new IllegalStateException("Utility class");
	}

	public static List<AuthorityDTO> authorityToDTO(User principal) {
		return principal.getAuthorities()
			.stream()
			.map(grantedAuthority -> {
				Authority a = (Authority) grantedAuthority;		
				Resource resource = a.getResource();
				
				String applicationName = UtilAuth.getApplicationName();
				String servername = resource.getServer().getName();
				
				String prefix = applicationName.equals(servername) ? Strings.EMPTY : servername;
				return new AuthorityDTO(prefix + "/" + resource.getPath(), resource.getMethod());
			})
			.collect(toList());
	}
	
	public static Set<String> groupAuthoritiesByService(User principal) {
		return principal.getAuthority().stream().map(a -> a.getResource().getServer().getName()).collect(toSet());
	}
}
