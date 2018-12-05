package com.brunnog.gateway.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtAuthenticationConfig {
	
    @Value("${sgs.security.jwt.header:Authorization}")
    private String header;

    @Value("${sgs.security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${sgs.security.jwt.expiration:7200000}")
    private int expiration; // 2 hours

    @Value("${sgs.security.jwt.secret}")
    private String secret;
    
    @Value("${sgs.security.jwt.authority.key}")
    private String authorityKey;   
    
    @Value("${server.servlet.context-path}")
    private String apiPrefix;
}
