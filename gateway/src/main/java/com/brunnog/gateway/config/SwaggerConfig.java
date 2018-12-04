package com.brunnog.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.brunnog.gateway.dto.request.LoginRequestDTO;
import com.brunnog.gateway.resource.LoginResource;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDescriptionReader;
import springfox.documentation.spring.web.scanners.ApiListingScanner;
import springfox.documentation.spring.web.scanners.ApiModelReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
	
	@Autowired
    private TypeResolver typeResolver;
	
	@Primary
	@Bean
	public ApiListingScanner addExtraOperations(ApiDescriptionReader apiDescriptionReader,
			ApiModelReader apiModelReader, DocumentationPluginsManager pluginsManager) {
		return new LoginOperations(apiDescriptionReader, apiModelReader, pluginsManager);
	}
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .additionalModels(typeResolver.resolve(LoginRequestDTO.class))
                .additionalModels(typeResolver.resolve(LoginResource.class));
    }
}
